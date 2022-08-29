package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User newUser(User user);
    User updatePassword(User user, String oldPassword, String newPassword);
    User updateFullName(User user, String fullName);
    User getCurrentuser();
    void updateUserData(String fullName);
}
