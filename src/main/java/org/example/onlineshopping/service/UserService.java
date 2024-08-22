package org.example.onlineshopping.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.entity.User;
import org.example.onlineshopping.enums.Role;
import org.example.onlineshopping.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User user) {
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public User getByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setUsername(newUser.getUsername());
            existingUser.setPassword(newUser.getPassword());
            existingUser.setEmail(newUser.getEmail());
            userRepository.save(existingUser);
            return existingUser;
        }else{
            return null;
        }
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }


}
