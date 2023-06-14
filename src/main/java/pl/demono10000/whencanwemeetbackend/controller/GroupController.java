package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.demono10000.whencanwemeetbackend.dto.CreateGroupDto;
import pl.demono10000.whencanwemeetbackend.dto.GroupResponseDto;
import pl.demono10000.whencanwemeetbackend.dto.GroupShortDto;
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
    public GroupResponseDto createGroup(@RequestBody CreateGroupDto createGroupDto) {
        return createGroupService.save(createGroupDto);
    }
    @PostMapping("/groups")
    public List<GroupShortDto> getUserGroups() {
        return groupService.getUserGroups();
    }
    @GetMapping("/{id}")
    public GroupResponseDto getGroup(@PathVariable("id") Long groupId) {
        return groupService.getGroupDetails(groupId);
    }
}
