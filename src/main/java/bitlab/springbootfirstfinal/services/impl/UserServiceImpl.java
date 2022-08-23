package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.models.Role;
import bitlab.springbootfirstfinal.models.User;
import bitlab.springbootfirstfinal.repository.RoleRepository;
import bitlab.springbootfirstfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findAllByEmail(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("USER NOT FOUND");
    }

    public User newUser(User user) {
        User checkUser = userRepository.findAllByEmail(user.getEmail());
        if (checkUser == null) {
            Role userDefaultRole = roleRepository.findByRole("ROLE_USER");
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(userDefaultRole);

            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    public User updatePassword(User user, String oldPassword, String newPassword) {
        User currentUser = userRepository.findById(user.getId()).orElse(null);
        if (currentUser != null) {
            if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
                return userRepository.save(currentUser);
            }
        }
        return null;
    }

    public User updateFullName(User user, String fullName) {
        User currentUser = userRepository.findById(user.getId()).orElse(null);
        if (currentUser != null) {
            currentUser.setFullName(fullName);
            return userRepository.save(currentUser);
        }
        return null;
    }
}
