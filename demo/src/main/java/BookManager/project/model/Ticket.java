package BookManager.project.model;

import java.util.Date;


/**
 *  通过ticket来核实登录状态
 *  Ticket的作用是记录在并发环境中，网页登录时确认操作的身份。
 *  当你登录的时候，服务器就会生成一张凭证，这张凭证是你专属的，不会给别人。服务器将这张凭
 *  证写到服务器本地的数据库中，同时随着你的登录写进你的浏览器里面，只要你用这个浏览器去访问网站，
 *  这个Cookie就会随着你的请求发送到服务器，，服务器就会去找之前写进浏览器的Cookie并去数据库找拿着这张票的人
 */

public class Ticket {

    private int id;

    /**
     * 相绑定的userId
     */
    private int userId;

    /**
     * t票实体
     */
    private String ticket;

    /**
     * 过期时间
     */
    private Date expiredAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    //过期时间
    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }
}
