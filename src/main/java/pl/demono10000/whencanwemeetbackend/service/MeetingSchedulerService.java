package pl.demono10000.whencanwemeetbackend.service;

import pl.demono10000.whencanwemeetbackend.dto.MeetingSchedulerDto;
import pl.demono10000.whencanwemeetbackend.model.*;
import pl.demono10000.whencanwemeetbackend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeetingSchedulerService {

    @Autowired
    private UserUnavailabilityRepository userUnavailabilityRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    public List<LocalDateTime> findAvailableMeetingTimes(MeetingSchedulerDto meetingSchedulerDto) {
        Long groupId = meetingSchedulerDto.groupId();
        LocalTime startTime = meetingSchedulerDto.startTime();
        LocalTime endTime = meetingSchedulerDto.endTime();

        Optional<Group> group = groupRepository.findById(groupId);

        if (group.isPresent()) {
            List<Meeting> meetings = meetingRepository.findByGroupId(groupId);
            List<UserUnavailability> unavailabilities = new ArrayList<>();

            for (User user : group.get().getUsers()) {
                unavailabilities.addAll(userUnavailabilityRepository.findByUserId(user.getId()));
            }

            List<LocalDateTime> proposedMeetingTimes = new ArrayList<>();
            LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC).withHour(startTime.getHour()).withMinute(startTime.getMinute());
            LocalDateTime endOfCurrentDay = currentTime.withHour(endTime.getHour()).withMinute(endTime.getMinute());

            // Petla w której szukamy 5 wolnych terminów.
            while (proposedMeetingTimes.size() < 5) {
                // Zakładamy, że termin jest dostępny dopóki nie znajdziemy kolizji.
                boolean isAvailable = true;

                // Sprawdzamy, czy proponowany termin koliduje z istniejącymi spotkaniami.
                for (Meeting meeting : meetings) {
                    if ((meeting.getStart_date().isAfter(currentTime) && meeting.getStart_date().isBefore(endOfCurrentDay)) ||
                            (meeting.getEnd_date().isAfter(currentTime) && meeting.getEnd_date().isBefore(endOfCurrentDay))) {
                        isAvailable = false;
                        break;
                    }
                }

                // Sprawdzamy, czy proponowany termin koliduje z niedostępnością użytkowników.
                for (UserUnavailability unavailability : unavailabilities) {
                    LocalDateTime unavailabilityStart = unavailability.getStart_date();
                    LocalDateTime unavailabilityEnd = unavailability.getEnd_date();

                    if ((unavailabilityStart.isAfter(currentTime) && unavailabilityStart.isBefore(endOfCurrentDay)) ||
                            (unavailabilityEnd.isAfter(currentTime) && unavailabilityEnd.isBefore(endOfCurrentDay))) {
                        isAvailable = false;
                        break;
                    }
                }

                // Jeżeli termin jest dostępny, dodajmy go do propozycji.
                if (isAvailable) {
                    proposedMeetingTimes.add(currentTime);
                }

                // Przechodzimy do następnego dnia.
                currentTime = currentTime.plusDays(1).withHour(startTime.getHour()).withMinute(startTime.getMinute());
                endOfCurrentDay = currentTime.withHour(endTime.getHour()).withMinute(endTime.getMinute());
            }

            return proposedMeetingTimes;
        }

        return Collections.emptyList();
    }


}
