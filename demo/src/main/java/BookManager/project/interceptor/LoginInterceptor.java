package BookManager.project.interceptor;

import BookManager.project.model.Ticket;
import BookManager.project.service.TicketService;
import BookManager.project.utils.CookieUtils;
import java.util.Date;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 此为一个拦截器，在Host拦截器识别Ticket成功之后，
 * 这个拦截器将会核实当前的Ticket，
 * 如果没有、过期、甚至是无效。则会返回至登录
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //没有t票，去登陆
        String t = CookieUtils.getCookie("t",request);
        if(StringUtils.isEmpty(t)){
            response.sendRedirect("/users/login");
            return false;
        }

        //无效t票，去登陆
        Ticket ticket = ticketService.getTicket(t);
        if(ticket == null){
            response.sendRedirect("/users/login");
            return false;
        }

        //过期t票，去登陆
        if(ticket.getExpiredAt().before(new Date())){
            response.sendRedirect("/users/login");
            return false;
        }

        return true;
    }
}