package pl.demono10000.whencanwemeetbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
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
}
