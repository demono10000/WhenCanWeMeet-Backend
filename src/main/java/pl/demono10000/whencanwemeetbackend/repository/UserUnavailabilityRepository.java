package pl.demono10000.whencanwemeetbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.demono10000.whencanwemeetbackend.model.UserUnavailability;

public interface UserUnavailabilityRepository extends JpaRepository<UserUnavailability, Long> {

}
