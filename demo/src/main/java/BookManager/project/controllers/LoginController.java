package BookManager.project.controllers;

import BookManager.project.biz.LoginBiz;
import BookManager.project.model.User;
import BookManager.project.service.UserService;
import BookManager.project.utils.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 *  控制器：用于控制登录。
 *
 */

@Controller
public class LoginController {

    @Autowired
    private LoginBiz loginBiz;

    @Autowired
    private UserService userService;



    //登录逻辑，进入登录界面
    @RequestMapping(path = {"/users/login"}, method = {RequestMethod.GET})
    public String login() {
        return "login/login";
    }

    //登录逻辑
    @RequestMapping(path = {"/users/login/do"}, method = {RequestMethod.POST})
    public String doLogin(
            Model model,
            HttpServletResponse response,
            //从web中获得的参数，通过RequestParam，赋值给此
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        try {
            //直接调用loginBiz中的login方法。将获取的email与ps置入。成功登录之后，将会返回Host
            String t = loginBiz.login(email, password);
            CookieUtils.writeCookie("t", t, response);
            return "redirect:/index";//跳转到相应的Action界面
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "404";
        }
    }


    @RequestMapping(path = {"/users/register"}, method = {RequestMethod.GET})
    public String register() {
        return "login/register";
    }
    //注册逻辑，与登录逻辑相似，调用loginBiz方法
    @RequestMapping(path = {"/users/register/do"}, method = {RequestMethod.POST})
    public String doRegister(
            Model model,
            HttpServletResponse response,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        try {
            String t = loginBiz.register(user);
            CookieUtils.writeCookie("t", t, response);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "404";
        }
    }


    //登出逻辑，调用loginBiz中的logout方法，销毁Ticket即可
    @RequestMapping(path = {"/users/logout/do"}, method = {RequestMethod.GET})
    public String doLogout(
            @CookieValue("t") String t
    ) {

        loginBiz.logout(t);
        return "redirect:/index";

    }
}
