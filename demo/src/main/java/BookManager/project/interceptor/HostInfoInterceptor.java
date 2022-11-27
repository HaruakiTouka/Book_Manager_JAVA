package BookManager.project.interceptor;

import BookManager.project.model.Ticket;
import BookManager.project.model.User;
import BookManager.project.service.TicketService;
import BookManager.project.service.UserService;
import BookManager.project.utils.ConcurrentUtils;
import BookManager.project.utils.CookieUtils;
import java.util.Date;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 此为一个拦截器，这个拦截器试图通过请求中的Cookie来寻找对应的Ticket，
 * 一旦寻找到Ticket并成功的从数据库中找到了对应的用户，就直接放入ConcurrentUtils中。
 *
 *
 * 因此可以实现：在登录一次之后，再进行其他的操作时，服务器都能识别操作用户是谁，甚至在关闭浏览器之后再次打开也不用重新登录，
 * 因为服务器跟与浏览器发送的请求中附带的Cookie对你的身份自动进行了认证。
 */
@Component
public class HostInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    /**
     * 为注入host信息
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String t = CookieUtils.getCookie("t", request);
        if (!StringUtils.isEmpty(t)) {
            Ticket ticket = ticketService.getTicket(t);
            if (ticket != null && ticket.getExpiredAt().after(new Date())) {
                User host = userService.getUser(ticket.getUserId());
                ConcurrentUtils.setHost(host);
            }
        }
        return true;
    }

}