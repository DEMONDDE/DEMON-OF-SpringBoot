package cn.mapper;


import cn.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {


    public Integer findTotalCount(User user);

    //分页查找用户，由于有不同类型的多个参数所以用@Param
    public List<User> findByPage(@Param("start") int start, @Param("rows") int rows, @Param("user") User user);


    //按id删除
    public void delUserById(Integer id);

    //按组删除
    public void delselectedByIds(int[] ids);

    //添加用户
    public void addUser(User user);

    public User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    public User findUserById(int id);

    public void updateUser(User user);
}
