package pl.demono10000.whencanwemeetbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pl.demono10000.whencanwemeetbackend.dto.AddUnavailabilityDto;
import pl.demono10000.whencanwemeetbackend.dto.UserUnavailabilityResponseDto;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.model.UserUnavailability;
import pl.demono10000.whencanwemeetbackend.repository.UserUnavailabilityRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;

@Service
@RequiredArgsConstructor
public class AddUnavailabilityService {

    private final UserUnavailabilityRepository userUnavailabilityRepository;

    public UserUnavailabilityResponseDto save(AddUnavailabilityDto addUnavailabilityDto) {
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

        UserUnavailabilityResponseDto responseDto = new UserUnavailabilityResponseDto();
        responseDto.setStartDate(savedUserUnavailability.getStart_date());
        responseDto.setEndDate(savedUserUnavailability.getEnd_date());
        responseDto.setUserId(savedUserUnavailability.getUser().getId());

        return responseDto;
    }
}
