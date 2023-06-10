package pl.demono10000.whencanwemeetbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UnavailableTimeDto {
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private List<String> repeatDaysOfWeek;
    private Integer repeatDaysInterval;
    private Integer repeatDayOfMonth;

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
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
