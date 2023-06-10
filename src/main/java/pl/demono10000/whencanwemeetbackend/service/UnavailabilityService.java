package pl.demono10000.whencanwemeetbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.model.UserUnavailability;
import pl.demono10000.whencanwemeetbackend.repository.UserUnavailabilityRepository;

@Service
@RequiredArgsConstructor
public class UnavailabilityService {

    private final UserUnavailabilityRepository userUnavailabilityRepository;

    @Transactional
    public void saveUnavailability(UserUnavailability unavailability, User user) {
        unavailability.setUser(user);
        userUnavailabilityRepository.save(unavailability);
    }
}
