package pl.demono10000.whencanwemeetbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.demono10000.whencanwemeetbackend.model.GroupInvitation;
import pl.demono10000.whencanwemeetbackend.model.User;

public interface InviteRepository extends JpaRepository<GroupInvitation, Long> {
    GroupInvitation[] findAllByUser(User user);
}
