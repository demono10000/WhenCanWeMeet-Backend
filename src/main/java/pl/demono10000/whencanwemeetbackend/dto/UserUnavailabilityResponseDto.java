package pl.demono10000.whencanwemeetbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserUnavailabilityResponseDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long userId;
    private long id;
    private List<String> repeatDaysOfWeek;
    private Integer repeatDaysInterval;
    private Integer repeatDayOfMonth;
    public List<String> getRepeatDaysOfWeek() {
        return repeatDaysOfWeek;
    }
    public Integer getRepeatDaysInterval() {
        return repeatDaysInterval;
    }
    public Integer getRepeatDayOfMonth() {
        return repeatDayOfMonth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRepeatDaysOfWeek(List<String> repeatDaysOfWeek) {
        this.repeatDaysOfWeek = repeatDaysOfWeek;
    }

    public void setRepeatDaysInterval(Integer repeatDaysInterval) {
        this.repeatDaysInterval = repeatDaysInterval;
    }


    public void setRepeatDayOfMonth(Integer repeatDayOfMonth) {
        this.repeatDayOfMonth = repeatDayOfMonth;
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

