package pl.demono10000.whencanwemeetbackend.service;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.dto.CreateGroupDto;
import pl.demono10000.whencanwemeetbackend.dto.GroupResponseDto;
import pl.demono10000.whencanwemeetbackend.dto.StatusAndMessageDto;
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
    public StatusAndMessageDto save(CreateGroupDto createGroupDto) {
        String groupName = createGroupDto.getGroupName();

        // Walidacja
        if (StringUtils.isBlank(groupName)) {
            return new StatusAndMessageDto(false, "Group name cannot be blank.");
        }

        if (groupName.length() > 100) {
            return new StatusAndMessageDto(false, "Group name cannot exceed 100 characters.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        // Sprawdzanie, czy istnieje grupa o takiej samej nazwie dla tego użytkownika
        if (groupRepository.findByNameAndOwner(groupName, user).isPresent()) {
            return new StatusAndMessageDto(false, "You already have a group with this name.");
        }

        Group group = Group.builder()
                .name(groupName)
                .owner(user)
                .build();
        group.setUsers(new ArrayList<>());
        user.getGroups().add(group);
        group.getUsers().add(user);
        Group savedGroup = groupRepository.save(group);
        userRepository.save(user);

        return new StatusAndMessageDto(true, "Group created successfully.");
    }

}
