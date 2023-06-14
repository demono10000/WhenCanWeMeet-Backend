package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.demono10000.whencanwemeetbackend.dto.RegisterDto;
import pl.demono10000.whencanwemeetbackend.dto.UserDto;
import pl.demono10000.whencanwemeetbackend.service.RegistrationService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/api/register")
    public RegisterDto register(@RequestBody UserDto userDto) {
        return registrationService.register(userDto);
    }
}
