package pl.demono10000.whencanwemeetbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UnavailableTimeDto (Long userId,
                                  LocalDateTime start_date,
                                  LocalDateTime end_date,
                                  List<String> repeatDaysOfWeek,
                                  Integer repeatDaysInterval,
                                  Integer repeatDayOfMonth) {
}
