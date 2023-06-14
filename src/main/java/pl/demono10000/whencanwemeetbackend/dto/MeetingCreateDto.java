package pl.demono10000.whencanwemeetbackend.dto;

import pl.demono10000.whencanwemeetbackend.model.Meeting;

import java.time.LocalDateTime;

public record MeetingCreateDto (LocalDateTime startDate, LocalDateTime endDate, Long groupId, String name) {
    public static MeetingCreateDto fromMeeting(Meeting meeting) {
        return new MeetingCreateDto(meeting.getStart_date(), meeting.getEnd_date(), meeting.getGroup().getId(), meeting.getName());
    }
}
