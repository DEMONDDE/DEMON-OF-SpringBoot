package cn.controller;


import cn.po.PageBean;
import cn.po.User;
import cn.service.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private Service userService;

    //分页查找类
    @RequestMapping("/findUserByPageServlet")
    public String findUserByPageServlet(int currentPage, int rows, String name, String address, String email, Model model){
        if(name == null){
            System.out.println("null");
        }else if (name == ""){
            System.out.println("kong");
        }
        //创建用户类
        User user = new User();
        if(StringUtils.isNotBlank(name)){
            user.setName(name);
        }
        if(StringUtils.isNotBlank(address)){
            user.setAddress(address);
        }
        if(StringUtils.isNotBlank(email)){
            user.setEmail(email);
        }
         //获取分页
        PageBean<User> pb = userService.findUserByPage(currentPage, rows, user);
        model.addAttribute("pb",pb);
        model.addAttribute("user", user);
        return "list";
    }

    //单个删除
    @RequestMapping("delUserServlet")
    public String delUserServlet(int id){
        userService.delUserById(id);
        return "forward:findUserByPageServlet?currentPage=1&rows=5";
    }

    //按组删除
    @RequestMapping("delSelectedServlet")
    public String  delSelectedServlet(int[] uid){
        System.out.println(uid);
        userService.delselectedByIds(uid);
        return "forward:findUserByPageServlet?currentPage=1&rows=5";
    }

    //添加用户
    @RequestMapping("addUserServlet")
    public String addUserServlet(User user){
        userService.addUser(user);
        return "redirect:findUserByPageServlet?currentPage=1&rows=5";
    }

    //验证码
    @RequestMapping("checkCodeServlet")
    public void checkCodeServlet(HttpSession session, HttpServletResponse response) throws IOException {
        //设置验证码的大小
        int width = 100;
        int height = 50;
        StringBuffer code = new StringBuffer();

        //在内存中创建验证么图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //美化图片
        Graphics g = image.getGraphics();//画笔对象
        g.setColor(Color.pink);
        g.fillRect(0,0,width,height);

        //画边框
        g.setColor(Color.black);
        g.drawRect(0, 0, width-1, height-1);

        //写验证码
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random r = new Random();
        for(int i = 1;i <= 4; i++){
            int index = r.nextInt(str.length());
            char c = str.charAt(index);
            code.append(c);
            g.drawString(c+"", width/5*i, height/2);
        }

//        //增加干扰线
//        g.setColor(Color.green);
//        for(int i = 0; i < 10; i++){
//            int x1 = r.nextInt(width);
//            int x2 = r.nextInt(width);
//            int y1 = r.nextInt(height);
//            int y2 = r.nextInt(height);
//            g.drawLine(x1, y1,x2, y2);
//        }

        //将图片输出
        ImageIO.write(image, "jpg",response.getOutputStream());

        //将验证码存入session
        String checkcode = code.toString();
        session.setAttribute("CHECKCODE_SERVER", checkcode);
    }

    //登陆
    @RequestMapping("loginServlet")
    public String loginServlet(HttpSession session, String verifycode, HttpServletRequest request, String username,String password){
        //获取验证码
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        
        if(!checkcode_server.equalsIgnoreCase(verifycode)){
            // 验证码不正确
            request.setAttribute("login_msg", "验证码错误!");
            return "login";
        }
        //获取用户名密码
        /**
         * 获取map 再获取set 再将set改为list以获取键，再根据键获取值
         */

        //查询用户是否存在
        User loginUser = userService.findUserByUsernameAndPassword(username, password);
        if( loginUser == null){
            //登陆失败
            request.setAttribute("login_msg", "用户名或密码错误");
            return "login";
        }else{
            //登录成功
            request.getSession().setAttribute("user", loginUser);
            return "redirect:index.jsp";
        }
    }

    //查找用户用于update.jsp的回显
    @RequestMapping("findUserServlet")
    public String findUserServlet(int id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user",user);
        return "update";
    }

    //用于更新用户信息
    @RequestMapping("updateUserServlet")
    public String updateUserServlet(HttpServletRequest request,User user){
        userService.updateUser(user);
        return "redirect:findUserByPageServlet?currentPage=1&rows=5";
    }
}
