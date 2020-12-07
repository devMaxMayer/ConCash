package BlinovMS.ConCash.service;

import BlinovMS.ConCash.entity.User;

public interface UserService {

    User saveUser(User user);

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);


}
