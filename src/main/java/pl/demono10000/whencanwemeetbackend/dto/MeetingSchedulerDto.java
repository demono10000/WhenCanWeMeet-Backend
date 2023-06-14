package pl.demono10000.whencanwemeetbackend.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record MeetingSchedulerDto (Long groupId, LocalTime startTime, LocalTime  endTime) {
}
