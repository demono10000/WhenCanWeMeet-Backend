package pl.demono10000.whencanwemeetbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.demono10000.whencanwemeetbackend.model.UserUnavailability;

import java.util.Collection;

public interface UserUnavailabilityRepository extends JpaRepository<UserUnavailability, Long> {

    Collection<? extends UserUnavailability> findByUserId(Long id);
}
