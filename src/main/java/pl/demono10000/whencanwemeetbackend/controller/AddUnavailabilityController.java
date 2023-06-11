package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.demono10000.whencanwemeetbackend.dto.AddUnavailabilityDto;
import pl.demono10000.whencanwemeetbackend.dto.UserUnavailabilityResponseDto;
import pl.demono10000.whencanwemeetbackend.service.AddUnavailabilityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unavailable")
public class AddUnavailabilityController {

    private final AddUnavailabilityService addUnavailabilityService;

    @PostMapping
    public UserUnavailabilityResponseDto addUnavailableTime(@RequestBody AddUnavailabilityDto addUnavailabilityDto) {
        return addUnavailabilityService.save(addUnavailabilityDto);
    }
}
