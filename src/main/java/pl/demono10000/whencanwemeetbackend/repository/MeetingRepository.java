package pl.demono10000.whencanwemeetbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.demono10000.whencanwemeetbackend.model.Meeting;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByGroupId(Long groupId);
}
