package BlinovMS.ConCash.service.impl;

import BlinovMS.ConCash.entity.Role;
import BlinovMS.ConCash.entity.User;
import BlinovMS.ConCash.repository.RoleRepository;
import BlinovMS.ConCash.repository.UserRepository;
import BlinovMS.ConCash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        Role userRole = roleRepository.findByName("ROLE_ USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}
