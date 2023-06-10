package pl.demono10000.whencanwemeetbackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private boolean isRecurring;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

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

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
