package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.models.Role;
import bitlab.springbootfirstfinal.models.User;
import bitlab.springbootfirstfinal.repository.RoleRepository;
import bitlab.springbootfirstfinal.repository.UserRepository;
import bitlab.springbootfirstfinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findAllByEmail(username);
//        if (user != null) {
//            return user;
//        }
//        throw new UsernameNotFoundException("USER NOT FOUND");
//    }

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

    public User getCurrentuser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (! (authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    public void updateUserData(String fullName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (! (authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            user.setFullName(fullName);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
