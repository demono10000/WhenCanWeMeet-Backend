package pl.demono10000.whencanwemeetbackend.dto;

public class GroupShortDto {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // constructor, getters, setters
    public GroupShortDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
