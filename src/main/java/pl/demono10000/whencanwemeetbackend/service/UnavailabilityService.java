package pl.demono10000.whencanwemeetbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.model.UserUnavailability;
import pl.demono10000.whencanwemeetbackend.repository.UserUnavailabilityRepository;

@Service
public class UnavailabilityService {

    private final UserUnavailabilityRepository userUnavailabilityRepository;

    public UnavailabilityService(UserUnavailabilityRepository userUnavailabilityRepository) {
        this.userUnavailabilityRepository = userUnavailabilityRepository;
    }

    @Transactional
    public void saveUnavailability(UserUnavailability unavailability, User user) {
        unavailability.setUser(user);
        userUnavailabilityRepository.save(unavailability);
    }
}
