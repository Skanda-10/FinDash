package FinanceDash.UserService;

import FinanceDash.UserService.POJO.Role;
import FinanceDash.UserService.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user, Role requestorRole) {
        if (requestorRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: Only Admins can add users.");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails, Role requestorRole) {
        if (requestorRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: Only Admins can update users.");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setRole(userDetails.getRole());
        existingUser.setStatus(userDetails.getStatus());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId, Role requestorRole) {
        if (requestorRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admins can Delete users");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers(Role requestorRole) {
        if (requestorRole != Role.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Access Denied: You do not have permission to manage users."
            );
        }
        return userRepository.findAll();
    }
}