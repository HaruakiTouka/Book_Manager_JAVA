package BookManager.project.utils;
import BookManager.project.model.User;


/**
 * 用来保存当前访问者的容器，对于并发状态下
 */

public class ConcurrentUtils {

    /**
     * ThreadLocal：相当于一个线程id-到user之间的key-value的map。
     *
     * 当web程序运行在web服务器中时，都是并发的环境，
     * 拿tomcat来说，对于每一个请求tomcat都会从自己维护的线程池中选一个线程去处理这个请求
     *
     */
    private static ThreadLocal<User> host = new ThreadLocal<>();

    public static User getHost(){
        return host.get();
    }

    public static void setHost(User user){
        host.set(user);
    }
}
