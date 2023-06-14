package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.demono10000.whencanwemeetbackend.dto.MeetingCreateDto;
import pl.demono10000.whencanwemeetbackend.dto.MeetingDto;
import pl.demono10000.whencanwemeetbackend.dto.MeetingSchedulerDto;
import pl.demono10000.whencanwemeetbackend.service.MeetingSchedulerService;
import pl.demono10000.whencanwemeetbackend.service.MeetingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting")
public class MeetingController {
    private final MeetingService meetingService;
    private final MeetingSchedulerService meetingSchedulerService;
    @PostMapping("/create")
    public MeetingDto createMeeting(@RequestBody MeetingCreateDto meetingCreateDto) {
        return meetingService.createMeeting(meetingCreateDto);
    }
    @PostMapping("/schedule")
    public List<LocalDateTime> scheduleMeeting(@RequestBody MeetingSchedulerDto meetingSchedulerDto) {
        return meetingSchedulerService.findAvailableMeetingTimes(meetingSchedulerDto);
    }
}
