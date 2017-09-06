package cc.ccoder.model.service;

import cc.ccoder.model.entity.User;

import java.util.List;

/**
 * Author    :  chencong
 * Time      :  2017/9/5 19:22
 * Package   :  cc.ccoder.model.service
 * Describe  :  TODO
 */
public interface IUserService {

    User userLogin(User user);

    boolean userRegister(User user);

    List<User> getUserLists();

    User getUserByUsername(String username);

    boolean deleteUserById(Integer id);
}
