package pl.demono10000.whencanwemeetbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    public void shouldGetGroupById() {
        // given
        final Long groupId = 1L;
        final Group group = new Group();
        group.setId(groupId);
        given(groupRepository.findById(groupId)).willReturn(Optional.of(group));

        // when
        Group result = groupService.getGroupById(groupId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(groupId);
    }
}