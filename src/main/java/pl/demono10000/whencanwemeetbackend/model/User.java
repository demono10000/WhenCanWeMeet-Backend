package pl.demono10000.whencanwemeetbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @NotNull
    private String password;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserUnavailability> unavailabilities = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Group> groups = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<GroupInvitation> invitations = new ArrayList<>();

}
