package pl.demono10000.whencanwemeetbackend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.dto.CreateGroupDto;
import pl.demono10000.whencanwemeetbackend.dto.GroupResponseDto;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CreateGroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    @Transactional
    public GroupResponseDto save(CreateGroupDto createGroupDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        Group group = Group.builder()
                .name(createGroupDto.groupName())
                .owner(user)
                .build();
        group.setUsers(new ArrayList<>());
        user.getGroups().add(group);
        group.getUsers().add(user);
        Group savedGroup = groupRepository.save(group);
        userRepository.save(user);

        GroupResponseDto responseDto = new GroupResponseDto();
        responseDto.setId(savedGroup.getId());
        responseDto.setName(savedGroup.getName());
        responseDto.setOwnerId(savedGroup.getOwner().getId());

        return responseDto;
    }
}
