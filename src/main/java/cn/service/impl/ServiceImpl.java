package cn.service.impl;


import cn.mapper.UserMapper;
import cn.po.PageBean;
import cn.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("service")
@Transactional
public class ServiceImpl implements cn.service.Service {

    @Resource
    private UserMapper dao;
    @Override
    public PageBean<User> findUserByPage(int currentPage, int rows, User user) {
        //创建页
        PageBean<User> pb = new PageBean<User>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //查询总记录数
//        int totalCount = dao.findTotalCount(user);
        int totalCount = 20;
        pb.setTotalCount(totalCount);
        //计算总页码
        int totalPage;
        totalPage = (totalCount % rows) == 0? (totalCount/rows) : (totalCount/rows)+1;
        pb.setTotalPage(totalPage);
        //判断页码是否超出范围
        if(currentPage <= 0 ){
            currentPage = 1;
        }else if (currentPage > totalPage){
            currentPage = totalPage;
        }
        //获得展示的记录
        int start = (currentPage - 1) * rows;
        List<User> list = dao.findByPage(start, rows, user);
        pb.setList(list);
        return pb;
    }

    //按id删除
    @Override
    public void delUserById(int id) {
        dao.delUserById(id);
    }

    //按一组删除
    @Override
    public void delselectedByIds(int[] ids) {
        dao.delselectedByIds(ids);
    }

    //添加用户
    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    //查找是否存在用户
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return dao.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public User findUserById(int id) {
        return dao.findUserById(id);
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }
}
