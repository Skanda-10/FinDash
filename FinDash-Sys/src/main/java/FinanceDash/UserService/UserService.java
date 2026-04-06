package FinanceDash.UserService;

import FinanceDash.UserService.POJO.Role;
import FinanceDash.UserService.POJO.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    User addUser(User user, Role role);
    User updateUser(Long id, User userDetails, Role role);
    void deleteUser(Long userId, Role role);
    List<User> getAllUsers(Role role);
}
