package pl.demono10000.whencanwemeetbackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserUnavailability {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime start_date;
    private LocalDateTime end_date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    private List<String> repeatDaysOfWeek = new ArrayList<>();

    private Integer repeatDaysInterval;
    private Integer repeatDayOfMonth;

    public UserUnavailability() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start_date;
    }

    public void setStart(LocalDateTime start) {
        this.start_date = start;
    }

    public LocalDateTime getEnd() {
        return end_date;
    }

    public void setEnd(LocalDateTime end) {
        this.end_date = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getRepeatDaysOfWeek() {
        return repeatDaysOfWeek;
    }

    public void setRepeatDaysOfWeek(List<String> repeatDaysOfWeek) {
        this.repeatDaysOfWeek = repeatDaysOfWeek;
    }

    public Integer getRepeatDaysInterval() {
        return repeatDaysInterval;
    }

    public void setRepeatDaysInterval(Integer repeatDaysInterval) {
        this.repeatDaysInterval = repeatDaysInterval;
    }

    public Integer getRepeatDayOfMonth() {
        return repeatDayOfMonth;
    }

    public void setRepeatDayOfMonth(Integer repeatDayOfMonth) {
        this.repeatDayOfMonth = repeatDayOfMonth;
    }
}
