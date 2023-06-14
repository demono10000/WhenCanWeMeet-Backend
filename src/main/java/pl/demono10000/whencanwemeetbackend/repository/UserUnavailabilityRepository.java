package pl.demono10000.whencanwemeetbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.model.UserUnavailability;

import java.util.Collection;
import java.util.List;

public interface UserUnavailabilityRepository extends JpaRepository<UserUnavailability, Long> {

    Collection<? extends UserUnavailability> findByUserId(Long id);

    List<UserUnavailability> findByUser(User user);
}
