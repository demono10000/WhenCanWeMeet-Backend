package pl.demono10000.whencanwemeetbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private boolean isRecurring;
    private String name;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
