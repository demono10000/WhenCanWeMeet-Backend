package pl.demono10000.whencanwemeetbackend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.dto.MeetingCreateDto;
import pl.demono10000.whencanwemeetbackend.dto.MeetingDto;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.Meeting;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.repository.MeetingRepository;

import java.io.Console;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final GroupRepository groupRepository;
    @Transactional
    public MeetingDto createMeeting(MeetingCreateDto meetingCreateDto) {
        Group group = groupRepository.findById(meetingCreateDto.groupId()).orElseThrow();
        Meeting meeting = Meeting.builder()
                .name(meetingCreateDto.name())
                .start_date(meetingCreateDto.startDate())
                .end_date(meetingCreateDto.endDate())
                .name(meetingCreateDto.name())
                .group(group)
                .build();
        Meeting savedMeeting = meetingRepository.save(meeting);

        group.getMeetings().add(savedMeeting);

        return MeetingDto.fromMeeting(savedMeeting);
    }
}
