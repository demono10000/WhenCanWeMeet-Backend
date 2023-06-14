package pl.demono10000.whencanwemeetbackend.dto;

public record UserDto (String username, String password) {
    public String getUsername() {
        return username;
    }

    public CharSequence getPassword() {
        return password;
    }
}

