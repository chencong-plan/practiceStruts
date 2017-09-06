package cc.ccoder.controler;

import cc.ccoder.model.entity.User;
import cc.ccoder.model.service.IUserService;
import cc.ccoder.model.service.impl.UserServiceImpl;

import java.util.List;

/**
 * Author    :  chencong
 * Time      :  2017/9/5 19:27
 * Package   :  cc.ccoder.controler
 * Describe  :  TODO
 */
public class UserAction {

    private User user;
    private List<User> userList;
    private String msg;
    private IUserService iUserService = new UserServiceImpl();


    public String userRegister() {
        System.out.println("注册的user:"+user);
        if (user == null) {
            return "register_fail";
        }
        if (iUserService.userRegister(user)) {
           userList = iUserService.getUserLists();
            return "success";
        }
        return "register_fail";
    }

    public String userLogin(){
        if (user == null){
            return "login_fail";
        }
        if (iUserService.userLogin(user) == null){
            return "login_fail";
        }
        return "userLogin";
    }

    public String getUsersLists(){
        userList = iUserService.getUserLists();
        if (userList == null){
            return "getLists_fail";
        }
        return "getUsersLists";
    }

    public String checkUser(){
        String username = user.getUsername();
        if (iUserService.getUserByUsername(username) == null){
            msg = "success";
        }else{
            msg = "fail";
        }
        return "success";

    }

    public String deleteUser(){
        System.out.println("删除的user:"+user);
        if (user == null){
            msg = "fail";
            return "fail";
        }
        if (iUserService.deleteUserById(user.getId())){
            msg = "success";
            return "success";
        }
        msg = "fail";
        return "fail";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
