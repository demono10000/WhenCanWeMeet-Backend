package pl.demono10000.whencanwemeetbackend.service;

import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow();
    }
}
