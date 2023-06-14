package pl.demono10000.whencanwemeetbackend.dto;

import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.Meeting;
import pl.demono10000.whencanwemeetbackend.model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupResponseDto {
    private Long id;
    private String name;
    private Long ownerId;
    private List<String> users;
    private List<MeetingDto> meetings;
    private String ownerName;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<MeetingDto> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<MeetingDto> meetings) {
        this.meetings = meetings;
    }

    public static GroupResponseDto fromGroup(Group group) {
        GroupResponseDto groupResponseDto = new GroupResponseDto();
        groupResponseDto.setId(group.getId());
        groupResponseDto.setName(group.getName());
        groupResponseDto.setOwnerId(group.getOwner().getId());
        List<String> usersString = new ArrayList<>();
        for (User user : group.getUsers()) {
            usersString.add(user.getUsername());
        }

        groupResponseDto.setUsers(usersString);
        List<MeetingDto> meetingDtos = new ArrayList<>();
        for (Meeting meeting : group.getMeetings()) {
            meetingDtos.add(MeetingDto.fromMeeting(meeting));
        }
        groupResponseDto.setMeetings(meetingDtos);
        return groupResponseDto;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}