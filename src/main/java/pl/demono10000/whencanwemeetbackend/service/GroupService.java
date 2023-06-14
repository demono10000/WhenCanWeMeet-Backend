package pl.demono10000.whencanwemeetbackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.dto.GroupResponseDto;
import pl.demono10000.whencanwemeetbackend.dto.GroupShortDto;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow();
    }
    public GroupResponseDto getGroupDetails(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow();
        return GroupResponseDto.fromGroup(group);
    }
    public List<GroupShortDto> getUserGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        return user.getGroups().stream()
                .map(group -> new GroupShortDto(group.getId(), group.getName()))
                .collect(Collectors.toList());
    }
}
