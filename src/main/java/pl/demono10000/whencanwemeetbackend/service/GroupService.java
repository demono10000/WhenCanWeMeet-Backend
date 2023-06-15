package pl.demono10000.whencanwemeetbackend.service;

import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.demono10000.whencanwemeetbackend.dto.GroupResponseDto;
import pl.demono10000.whencanwemeetbackend.dto.GroupShortDto;
import pl.demono10000.whencanwemeetbackend.dto.RemoveUserFromGroupDto;
import pl.demono10000.whencanwemeetbackend.dto.StatusAndMessageDto;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    @Autowired
    private final GroupRepository groupRepository;
    @Autowired
    private final UserRepository userRepository;

    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow();
    }
    @Transactional
    public GroupResponseDto getGroupDetails(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        try{
            User user = userRepository.findById(userPrincipal.getUser().getId()).get();
            if(!group.getUsers().contains(user)){
                throw new RuntimeException("User is not in the group");
            }
        } catch (Exception e){
            throw new RuntimeException("User is not in the group");
        }
        GroupResponseDto responseDto = GroupResponseDto.fromGroup(group);
        responseDto.setOwnerName(group.getOwner().getUsername());
        return responseDto;
    }
    public List<GroupShortDto> getUserGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        return user.getGroups().stream()
                .map(group -> new GroupShortDto(group.getId(), group.getName()))
                .collect(Collectors.toList());
    }

    public StatusAndMessageDto removeUser(Long id, RemoveUserFromGroupDto userName) {
        System.out.println("removeUser " + userName.userName() + " from group " + id);
        Group group;
        try{
            group = groupRepository.findById(id)
                    .orElseThrow();
        } catch (Exception e){
            return new StatusAndMessageDto(false, "Group not found");
        }

        User user;
        try{
            user = userRepository.findByUsername(userName.userName())
                    .orElseThrow();
        } catch (Exception e){
            return new StatusAndMessageDto(false, "User not found");
        }
        if(!group.getUsers().contains(user)){
            return new StatusAndMessageDto(false, "User is not in the group");
        }
        if(group.getOwner().getId().equals(user.getId())){
            return new StatusAndMessageDto(false, "User is owner of the group");
        }
        group.getUsers().remove(user);
        groupRepository.save(group);
        user.getGroups().remove(group);
        userRepository.save(user);
        return new StatusAndMessageDto(true, "User removed from the group");
    }
}
