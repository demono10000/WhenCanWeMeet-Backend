package pl.demono10000.whencanwemeetbackend.service;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.dto.MeetingCreateDto;
import pl.demono10000.whencanwemeetbackend.dto.StatusAndMessageDto;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.Meeting;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.repository.MeetingRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public StatusAndMessageDto createMeeting(MeetingCreateDto meetingCreateDto) {
        if (meetingCreateDto == null) {
            return new StatusAndMessageDto(false, "Meeting data cannot be null.");
        }

        if (StringUtils.isEmpty(meetingCreateDto.name())) {
            return new StatusAndMessageDto(false, "Meeting name cannot be empty.");
        }
        if (StringUtils.isBlank(meetingCreateDto.name())) {
            return new StatusAndMessageDto(false, "Group name cannot be blank.");
        }

        if (meetingCreateDto.startDate() == null) {
            return new StatusAndMessageDto(false, "Meeting start date cannot be null.");
        }

        if (meetingCreateDto.endDate() == null) {
            return new StatusAndMessageDto(false, "Meeting end date cannot be null.");
        }

        if (meetingCreateDto.startDate().isBefore(LocalDateTime.now())) {
            return new StatusAndMessageDto(false, "Meeting start date cannot be in the past.");
        }

        if (meetingCreateDto.endDate().isBefore(meetingCreateDto.startDate())) {
            return new StatusAndMessageDto(false, "Meeting end date cannot be before the start date.");
        }

        Group group = groupRepository.findById(meetingCreateDto.groupId()).orElse(null);

        if (group == null) {
            return new StatusAndMessageDto(false, "Group not found.");
        }

        // Odczytanie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        // Walidacja czy użytkownik jest właścicielem grupy
        if (!Objects.equals(group.getOwner().getId(), user.getId())) {
            return new StatusAndMessageDto(false, "You are not the owner of this group.");
        }

        try {
            Meeting meeting = Meeting.builder()
                    .name(meetingCreateDto.name())
                    .start_date(meetingCreateDto.startDate())
                    .end_date(meetingCreateDto.endDate())
                    .group(group)
                    .build();

            Meeting savedMeeting = meetingRepository.save(meeting);

            group.getMeetings().add(savedMeeting);

        } catch (DataIntegrityViolationException e) {
            return new StatusAndMessageDto(false, "Error during meeting creation. Please try again.");
        }

        return new StatusAndMessageDto(true, "Meeting created successfully.");
    }
}

