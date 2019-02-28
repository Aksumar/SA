package com.example.SA.Service;

import com.example.SA.domain.User.Role;
import com.example.SA.domain.User.User;
import com.example.SA.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
//    @Autowired
//    private MailSenderService mailSenderService;

    @Override
    public UserDetails loadUserByUsername(String usernameToLoadInfo) throws UsernameNotFoundException {
        return userRepo.findByUsername(usernameToLoadInfo);
    }

    public boolean addUserSuccessful(User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s!\n" +
                            "Welcome to SurveyAnalyzer. Your activation link : http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
           // mailSenderService.send(user.getEmail(), "ActivationCode", message);
        }
        return true;
    }
}
