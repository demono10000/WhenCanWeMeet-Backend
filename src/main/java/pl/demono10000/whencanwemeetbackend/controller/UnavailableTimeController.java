package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.demono10000.whencanwemeetbackend.dto.UnavailableTimeDto;
import pl.demono10000.whencanwemeetbackend.model.UserUnavailability;
import pl.demono10000.whencanwemeetbackend.service.UnavailabilityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unavailable")
public class UnavailableTimeController {

    private final UnavailabilityService unavailabilityService;

    @PostMapping
    public ResponseEntity<Void> addUnavailableTime(@RequestBody UnavailableTimeDto unavailableTimeDto) {
        UserUnavailability userUnavailability = new UserUnavailability();
        // tutaj musisz przekonwertować DTO na model (UserUnavailability)
        // i zapisać w bazie danych za pomocą unavailabilityService
        return ResponseEntity.ok().build();
    }
}
