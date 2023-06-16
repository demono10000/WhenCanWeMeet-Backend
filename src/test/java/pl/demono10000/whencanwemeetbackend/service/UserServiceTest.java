package pl.demono10000.whencanwemeetbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;
import pl.demono10000.whencanwemeetbackend.service.UserService;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldFindUserByUsername() {
        // given
        final String username = "test";
        final User user = new User();
        user.setUsername(username);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

        // when
        User result = userService.findByUsername(username);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(username);
    }

}
