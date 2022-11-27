package BookManager.project.service;

import BookManager.project.model.User;
import BookManager.project.utils.ConcurrentUtils;
import org.springframework.stereotype.Service;

/**
 *  服务级；承接前端，调用后端
 *  处理登录用户的host，并且包装ConcurrentUtils的方法
 *  只要用户登录了，便可以通过Host，来确定User
 */

@Service
public class HostHolder {

    //获得当前用户的Host
    public User getUser() {
        return ConcurrentUtils.getHost();
    }

    public void setUser(User user) {
        ConcurrentUtils.setHost(user);
    }
}
