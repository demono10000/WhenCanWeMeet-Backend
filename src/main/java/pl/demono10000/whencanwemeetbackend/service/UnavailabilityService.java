package pl.demono10000.whencanwemeetbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pl.demono10000.whencanwemeetbackend.dto.AddUnavailabilityDto;
import pl.demono10000.whencanwemeetbackend.dto.StatusAndMessageDto;
import pl.demono10000.whencanwemeetbackend.dto.UserUnavailabilityResponseDto;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.model.UserUnavailability;
import pl.demono10000.whencanwemeetbackend.repository.UserUnavailabilityRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnavailabilityService {

    private final UserUnavailabilityRepository userUnavailabilityRepository;

    public StatusAndMessageDto save(AddUnavailabilityDto addUnavailabilityDto) {
        // Check if dto is null
        if (addUnavailabilityDto == null) {
            return new StatusAndMessageDto(false, "Unavailability data cannot be null.");
        }

        // Check if start and end dates are valid
        if (addUnavailabilityDto.start_date() == null || addUnavailabilityDto.end_date() == null) {
            return new StatusAndMessageDto(false, "Start and end dates cannot be null.");
        }

        if (addUnavailabilityDto.start_date().compareTo(addUnavailabilityDto.end_date()) > 0) {
            return new StatusAndMessageDto(false, "Start date cannot be after end date.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        UserUnavailability userUnavailability = UserUnavailability.builder()
                .user(user)
                .start_date(addUnavailabilityDto.start_date())
                .end_date(addUnavailabilityDto.end_date())
                .repeatDaysOfWeek(addUnavailabilityDto.repeatDaysOfWeek())
                .repeatDayOfMonth(addUnavailabilityDto.repeatDayOfMonth())
                .repeatDaysInterval(addUnavailabilityDto.repeatDaysInterval())
                .build();
        UserUnavailability savedUserUnavailability = userUnavailabilityRepository.save(userUnavailability);

        return new StatusAndMessageDto(true, "Unavailability saved");
    }
    public List<UserUnavailabilityResponseDto> getUnavailabilities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        List<UserUnavailability> unavailabilities = userUnavailabilityRepository.findByUser(user);
        List<UserUnavailabilityResponseDto> responseDtos = new ArrayList<>();

        for(UserUnavailability unavailability : unavailabilities) {
            // sprawdzamy, czy "repeatDaysOfWeek" zawiera niepuste stringi
            boolean repeatDaysOfWeekIsNotEmpty = unavailability.getRepeatDaysOfWeek() != null
                    && unavailability.getRepeatDaysOfWeek().stream().anyMatch(day -> !day.trim().isEmpty());

            // jeśli unavailability ma ustawioną datę końca w przeszłości i nie ma powtarzania
            if (unavailability.getEnd_date().isBefore(LocalDateTime.now())
                    && !repeatDaysOfWeekIsNotEmpty
                    && unavailability.getRepeatDayOfMonth() == null
                    && unavailability.getRepeatDaysInterval() == null) {
                continue;  // pomiń tę niedostępność
            }

            UserUnavailabilityResponseDto responseDto = new UserUnavailabilityResponseDto();
            responseDto.setStartDate(unavailability.getStart_date());
            responseDto.setEndDate(unavailability.getEnd_date());
            responseDto.setUserId(unavailability.getUser().getId());
            responseDto.setRepeatDayOfMonth(unavailability.getRepeatDayOfMonth());
            responseDto.setRepeatDaysOfWeek(unavailability.getRepeatDaysOfWeek());
            responseDto.setRepeatDaysInterval(unavailability.getRepeatDaysInterval());
            responseDto.setId(unavailability.getId());

            responseDtos.add(responseDto);
        }

        // sortuj od najstarszych do najnowszych
        responseDtos.sort(Comparator.comparing(UserUnavailabilityResponseDto::getStartDate));

        return responseDtos;
    }

    public StatusAndMessageDto deleteUnavailability(Long unavailabilityId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        UserUnavailability unavailability = userUnavailabilityRepository.findById(unavailabilityId)
                .orElseThrow(() -> new RuntimeException("Unavailability not found"));
        if (!unavailability.getUser().getId().equals(user.getId())) {
            return new StatusAndMessageDto(false, "You are not the owner of this unavailability");
        }
        userUnavailabilityRepository.delete(unavailability);
        return new StatusAndMessageDto(true, "Unavailability deleted");
    }
}
