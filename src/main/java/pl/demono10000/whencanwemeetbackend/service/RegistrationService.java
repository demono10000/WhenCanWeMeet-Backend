package pl.demono10000.whencanwemeetbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.demono10000.whencanwemeetbackend.dto.RegisterDto;
import pl.demono10000.whencanwemeetbackend.dto.UserDto;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public RegisterDto register(UserDto userDto) {
        if (userDto == null) {
            return new RegisterDto(false, "User data cannot be null.");
        }

        if (StringUtils.isEmpty(userDto.getUsername())) {
            return new RegisterDto(false, "Username cannot be empty.");
        }

        if (StringUtils.isEmpty(userDto.getPassword())) {
            return new RegisterDto(false, "Password cannot be empty.");
        }

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return new RegisterDto(false, "Username already taken.");
        }

        try {
            User user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();

            userRepository.save(user);

        } catch (DataIntegrityViolationException e) {
            return new RegisterDto(false, "Error during registration. Please try again.");
        }

        return new RegisterDto(true, "User registered successfully.");
    }
}
