package cc.ccoder.model.service.impl;

import cc.ccoder.model.dao.IUserDao;
import cc.ccoder.model.dao.impl.UserDaoImpl;
import cc.ccoder.model.entity.User;
import cc.ccoder.model.service.IUserService;
import cc.ccoder.until.DBOperatorUtils;

import java.util.List;

/**
 * Author    :  chencong
 * Time      :  2017/9/5 19:23
 * Package   :  cc.ccoder.model.service.impl
 * Describe  :  TODO
 */
public class UserServiceImpl implements IUserService {

    private IUserDao iUserDao = new UserDaoImpl();

    @Override
    public User userLogin(User user) {
        if (user == null) {
            return null;
        }
        return iUserDao.userLogin(user);
    }

    @Override
    public boolean userRegister(User user) {
        if (user == null) {
            return false;
        }
        return iUserDao.userRegister(user);
    }

    @Override
    public List<User> getUserLists() {
        return iUserDao.getUserLists();
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) {
            return null;
        }
        return iUserDao.getUserByUsername(username);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        if (id == null) {
            return false;
        }
        return iUserDao.deleteUserById(id);
    }
}
