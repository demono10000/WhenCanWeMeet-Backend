package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.demono10000.whencanwemeetbackend.dto.*;
import pl.demono10000.whencanwemeetbackend.service.CreateGroupService;
import pl.demono10000.whencanwemeetbackend.service.GroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {
    private final CreateGroupService createGroupService;
    private final GroupService groupService;

    @PostMapping("/create")
    public StatusAndMessageDto createGroup(@RequestBody CreateGroupDto createGroupDto) {
        return createGroupService.save(createGroupDto);
    }
    @PostMapping("/groups")
    public List<GroupShortDto> getUserGroups() {
        return groupService.getUserGroups();
    }
    @PostMapping("/{id}")
    public GroupResponseDto getGroup(@PathVariable("id") Long groupId) {
        return groupService.getGroupDetails(groupId);
    }
    @PostMapping("/removeUser/{id}")
    public StatusAndMessageDto removeUser(@PathVariable("id") Long groupId, @RequestBody RemoveUserFromGroupDto userName) {
        return groupService.removeUser(groupId, userName);
    }
}
