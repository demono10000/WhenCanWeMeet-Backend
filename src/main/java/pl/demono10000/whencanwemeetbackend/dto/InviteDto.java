package pl.demono10000.whencanwemeetbackend.dto;

public record InviteDto (String username, long groupId){
    public String getUsername() {
        return username;
    }

    public Long getGroupId() {
        return groupId;
    }
}
