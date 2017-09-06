package cc.ccoder.model.dao;

import cc.ccoder.model.entity.User;

import java.util.List;

/**
 * Author    :  chencong
 * Time      :  2017/9/5 19:16
 * Package   :  cc.ccoder.model.dao
 * Describe  :  TODO
 */
public interface IUserDao {

    User userLogin(User user);

    boolean userRegister(User user);

    List<User> getUserLists();

    User getUserByUsername(String username);

    boolean deleteUserById(Integer id);

}
