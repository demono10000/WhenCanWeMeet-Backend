package pl.demono10000.whencanwemeetbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
