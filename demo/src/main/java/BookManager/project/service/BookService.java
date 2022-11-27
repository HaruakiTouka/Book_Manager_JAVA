package BookManager.project.service;
import BookManager.project.dao.BookDAO;

import BookManager.project.model.Book;
import BookManager.project.model.enums.BookStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.apache.log4j.Logger;

@Service
public class BookService {
    /**
     * 服务级
     *  对BookDAO.java的这一接口做一些封装，你可以认为是一种代理（Proxy）模式
     *  与BookDAO不一样，BookDAO接口实现与数据库打交道，需要尽量描述自己的功能。
     *  而Service层关心的是功能，就是说，根据Name也好还是根据id也好在上层来看并没有区别，都是给我去取一本书来
     *  不用管是根据书的什么属性去取。所以需要重构
     */

//

    @Autowired
    private BookDAO bookDAO;

    public List<Book> getAllBooks() {
        /**
         *  异常需要
         */
        try {
            return bookDAO.selectAll();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("Tushu_Logger_Debug");
            logger.error("selectAll异常",e);
            logger.warn("异常",e);
            return null;
            /*- 或者抛出自定义的异常
            error 为严重错误 主要是程序的错误
            warn 为一般警告，比如session丢失
            info 为一般要显示的信息，比如登录登出
            debug 为程序的调试信息
            -*/
        }
    }

    public int addBooks(Book book) {
        return bookDAO.addBook(book);
    }

    public void deleteBooks(int id) {
        bookDAO.updateBookStatus(id, BookStatusEnum.DELETE.getValue());
    }

    public void recoverBooks(int id) {
        bookDAO.updateBookStatus(id, BookStatusEnum.NORMAL.getValue());
    }

    Book getBook(int id) {
        return bookDAO.selectBookById(id);
    }

    Book getBook(String name) {
        return bookDAO.selectBookByName(name);
    }

}
