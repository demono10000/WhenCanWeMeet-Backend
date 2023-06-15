package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.demono10000.whencanwemeetbackend.dto.AddUnavailabilityDto;
import pl.demono10000.whencanwemeetbackend.dto.StatusAndMessageDto;
import pl.demono10000.whencanwemeetbackend.dto.UserUnavailabilityResponseDto;
import pl.demono10000.whencanwemeetbackend.service.UnavailabilityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unavailable")
public class UnavailabilityController {

    private final UnavailabilityService UnavailabilityService;

    @PostMapping("/add")
    public StatusAndMessageDto addUnavailableTime(@RequestBody AddUnavailabilityDto addUnavailabilityDto) {
        return UnavailabilityService.save(addUnavailabilityDto);
    }
    @PostMapping("/get")
    public List<UserUnavailabilityResponseDto> getUnavailableTime() {
        return UnavailabilityService.getUnavailabilities();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUnavailableTime(@PathVariable Long id) {
        UnavailabilityService.deleteUnavailability(id);
    }
}
