package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public interface UserService extends UserDetailsService {
    User newUser(User user);
    User updatePassword(User user, String oldPassword, String newPassword);
    User updateFullName(User user, String fullName);
    User getCurrentuser();
    List<User> getAllUsers();
    User getUserByEmail(String email);
    void updateUserData(String fullName);
}
