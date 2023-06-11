package pl.demono10000.whencanwemeetbackend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.dto.InviteDto;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.GroupInvitation;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.repository.InviteRepository;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    @Transactional
    public InviteDto send(InviteDto inviteDto) {
        UserService userService = new UserService(userRepository);
        User user = userService.findByUsername(inviteDto.username());
        GroupService groupService = new GroupService(groupRepository);
        Group group = groupService.getGroupById(inviteDto.groupId());
        GroupInvitation groupInvitation =  GroupInvitation.builder()
                .user(user)
                .group(group)
                .build();
        inviteRepository.save(groupInvitation);
        InviteDto responseDto = new InviteDto(
                inviteDto.username(),
                inviteDto.groupId()
        );
        return responseDto;
    }
}
