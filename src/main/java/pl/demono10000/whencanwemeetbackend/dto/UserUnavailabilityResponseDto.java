package pl.demono10000.whencanwemeetbackend.dto;

import java.time.LocalDateTime;

public class UserUnavailabilityResponseDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long userId;

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

