package FinanceDash.UserService;

import FinanceDash.UserService.POJO.Role;
import FinanceDash.UserService.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(
            @RequestBody User user,
            @RequestHeader("X-User-Role") Role requestorRole) {
        return ResponseEntity.ok(userService.addUser(user, requestorRole));
    }

    // UPDATE USER - HTTP PUT
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userDetails,
            @RequestHeader("X-User-Role") Role requestorRole) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails, requestorRole));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestHeader("X-User-Role") Role requestorRole) {

        List<User> users = userService.getAllUsers(requestorRole);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            @RequestHeader("X-User-Role") Role requestorRole) {

        userService.deleteUser(id, requestorRole);
        return ResponseEntity.ok("User deleted successfully");
    }
}