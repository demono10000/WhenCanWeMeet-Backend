package pl.demono10000.whencanwemeetbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.User;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Object> findByNameAndOwner(String groupName, User user);
}
