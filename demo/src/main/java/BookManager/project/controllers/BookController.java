package BookManager.project.controllers;

import BookManager.project.model.User;
import BookManager.project.service.BookService;
import BookManager.project.model.Book;
import BookManager.project.service.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MVC中的Controller：控制器
 */
@Controller
public class BookController {

    /**
     * Autowired：与new的区别，Autowired能够判断是否已有此对象，无再创建
     */
    @Autowired
    private BookService bookService;

    @Autowired
    private HostHolder hostHolder;


    /**
     * RequestMapping：告诉web需要这样的url才能进入这个方法
     */
    @RequestMapping(path = {"/index"}, method = {RequestMethod.GET})
    public String bookList(Model model) {
        User host = hostHolder.getUser();//获得当前登录用户的Host
        if (host != null) {
            model.addAttribute("host", host);
        }
        loadAllBooksView(model);
        return "book/books";//返回新的网址

    }

    @RequestMapping(path = {"/books/add"}, method = {RequestMethod.GET})
    public String addBook() {
        return "book/addbook";
    }

    /**
     * doAddBook：添加图书
     */
    @RequestMapping(path = {"/books/add/do"}, method = {RequestMethod.POST})
    public String doAddBook(
//          从web中获得的参数，通过RequestParam，赋值给此
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("price") String price
    ) {

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        bookService.addBooks(book);

        return "redirect:/index";

    }


    @RequestMapping(path = {"/books/{bookId:[0-9]+}/delete"}, method = {RequestMethod.GET})
    public String deleteBook(
            @PathVariable("bookId") int bookId
    ) {
        bookService.deleteBooks(bookId);
        return "redirect:/index";

    }

    @RequestMapping(path = {"/books/{bookId:[0-9]+}/recover"}, method = {RequestMethod.GET})
    public String recoverBook(
            @PathVariable("bookId") int bookId
    ) {

        bookService.recoverBooks(bookId);
        return "redirect:/index";

    }

    /**
     * 为model加载所有的book
     */
    private void loadAllBooksView(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
    }

}
