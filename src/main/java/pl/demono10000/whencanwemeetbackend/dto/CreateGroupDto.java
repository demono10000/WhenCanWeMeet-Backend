package pl.demono10000.whencanwemeetbackend.dto;

public record CreateGroupDto (String groupName) {
    public String getGroupName() {
        return groupName;
    }
}
