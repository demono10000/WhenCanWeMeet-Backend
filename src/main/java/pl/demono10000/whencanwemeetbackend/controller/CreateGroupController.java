package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.demono10000.whencanwemeetbackend.dto.CreateGroupDto;
import pl.demono10000.whencanwemeetbackend.dto.GroupResponseDto;
import pl.demono10000.whencanwemeetbackend.service.CreateGroupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/createGroup")
public class CreateGroupController {
    private final CreateGroupService createGroupService;

    @PostMapping
    public GroupResponseDto createGroup(@RequestBody CreateGroupDto createGroupDto) {
        return createGroupService.save(createGroupDto);
    }
}
