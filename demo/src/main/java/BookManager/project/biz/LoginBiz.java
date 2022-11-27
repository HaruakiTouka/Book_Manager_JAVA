package BookManager.project.biz;


import BookManager.project.model.User;
import BookManager.project.model.exceptions.LoginRegisterException;

import BookManager.project.model.Ticket;
import BookManager.project.service.TicketService;
import BookManager.project.service.UserService;
import BookManager.project.utils.ConcurrentUtils;
import BookManager.project.utils.MD5;
import BookManager.project.utils.TicketUtils;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 服务级
 * 登录逻辑相关，
 * 向上给予{@link BookManager.project.controllers.LoginController}登录登出各种服务
 * 向下调用{@link BookManager.project.service}中的各种服务，来查询数据库中的登录信息
 */
@Service
public class LoginBiz {
    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;
    /**
     * 登录逻辑，先检查邮箱和密码，然后更新Ticket。
     * @return 返回最新Ticket
     * @throws LoginRegisterException 账号密码错误
     */
    public String login(String email, String password) throws Exception {
        User user = userService.getUser(email);

        //登录信息检查
        if (user == null)//到数据库中查找邮箱
            throw new LoginRegisterException("邮箱不存在");

        //邮箱存在，则比较数据库中存的密码与md5的密码是否一致
        if(!StringUtils.equals(MD5.next(password),user.getPassword()))
            throw new LoginRegisterException("密码不正确");

        //检查ticket，用ticket来维护是否处于登录状态
        Ticket t = ticketService.getTicket(user.getId());
        //如果没有ticket。生成一个
        if(t == null){
            t = TicketUtils.next(user.getId());
            ticketService.addTicket(t);
            //将用户信息加入ConcurrentUtils中，供HostHolder使用
            ConcurrentUtils.setHost(user);
            //直接返回
            return t.getTicket();
        }
        //是否过期
        if(t.getExpiredAt().before(new Date())){
            //删除
            ticketService.deleteTicket(t.getId());
        }
        //将过期的更新
        t = TicketUtils.next(user.getId());
        ticketService.addTicket(t);
        //将用户信息加入ConcurrentUtils中，供HostHolder使用
        ConcurrentUtils.setHost(user);
        return t.getTicket();
    }

    /**
     * 用户退出登录，只需要删除数据库中用户的Ticket
     */
    public void logout(String t){
        ticketService.deleteTicket(t);
    }

    /**
     * 注册一个用户，并返回用户Ticket
     */
    public String register(User user) throws Exception {

        //信息检查
        if (userService.getUser(user.getEmail()) != null) {
            throw new LoginRegisterException("用户邮箱已经存在！");
        }

        //密码加密，数据库中存储的直接就是加密的密码
        String plain = user.getPassword();
        String md5 = MD5.next(plain);
        user.setPassword(md5);
        //数据库添加用户
        userService.addUser(user);

        //生成用户t票
        Ticket ticket = TicketUtils.next(user.getId());
        //数据库添加t票
        ticketService.addTicket(ticket);
        //将用户信息加入ConcurrentUtils中，供HostHolder使用
        ConcurrentUtils.setHost(user);
        return ticket.getTicket();

    }
}
