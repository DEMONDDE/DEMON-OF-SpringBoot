package cn.service;

import cn.po.PageBean;
import cn.po.User;


public interface Service  {

    PageBean<User> findUserByPage(int currentPage, int rows, User user);

    public void delUserById(int id);

    public void delselectedByIds(int[] ids);

    public void addUser(User user);
    public User findUserByUsernameAndPassword(String username, String password);

    public User findUserById(int id);

    public void updateUser(User user);
}
