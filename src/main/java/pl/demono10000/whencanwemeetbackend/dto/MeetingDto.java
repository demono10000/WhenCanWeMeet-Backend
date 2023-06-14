package pl.demono10000.whencanwemeetbackend.dto;

import pl.demono10000.whencanwemeetbackend.model.Meeting;

public record MeetingDto (String startDate, String endDate, String name) {
    public static MeetingDto fromMeeting(Meeting meeting) {
        return new MeetingDto(meeting.getStart_date().toString(), meeting.getEnd_date().toString(), meeting.getName());
    }
}
