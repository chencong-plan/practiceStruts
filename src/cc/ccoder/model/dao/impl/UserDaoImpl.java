package cc.ccoder.model.dao.impl;

import cc.ccoder.model.dao.IUserDao;
import cc.ccoder.model.entity.User;
import cc.ccoder.until.DBOperatorUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Author    :  chencong
 * Time      :  2017/9/5 19:18
 * Package   :  cc.ccoder.model.dao.impl
 * Describe  :  TODO
 */
public class UserDaoImpl implements IUserDao {


    @Override
    public User userLogin(User user) {
        String sql = "select * from users where username = ? and password = ?";
        List<Object> params = Arrays.asList(user.getUsername(), user.getPassword());
        return DBOperatorUtils.getSimpleResult(sql, params, User.class);
    }

    @Override
    public boolean userRegister(User user) {
        String sql = "insert into users (username,password) values (?,?)";
        List<Object> params = Arrays.asList(user.getUsername(), user.getPassword());
        return DBOperatorUtils.executeUpdateResult(sql, params);
    }

    @Override
    public List<User> getUserLists() {
        String sql = "select * from users";
        List<Object> params = Arrays.asList();
        return DBOperatorUtils.getMoreResult(sql, params, User.class);
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "select * from users where username = ?";
        List<Object> params = Arrays.asList(username);
        return DBOperatorUtils.getSimpleResult(sql, params, User.class);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        String sql = "delete from users where id = ?";
        List<Object> params = Arrays.asList(id);
        return DBOperatorUtils.executeUpdateResult(sql, params);
    }
}
