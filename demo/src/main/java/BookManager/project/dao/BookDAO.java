package BookManager.project.dao;

import BookManager.project.model.Book;
import java.util.List;

import org.apache.ibatis.annotations.*;

/**
 * 用于连接以及针对数据库的各种操作
 * 用作接口，因此均为未具体实现的方法。在此之上则是这些方法的注解
 * DAO层关心的就是跟数据库打交道，这样
 * 所有的方法名都应该要尽量的去描述自己的功能
 */

@Mapper
public interface BookDAO {

    String table_name = " book ";
    String insert_field = " name, author, price ";
    String select_field = " id, status, " + insert_field;
    //将程序中不变的部分和需要变化的部分分开是设计模式中的一项基本原则，这里很显然数据库表名、插入和选择范围都是基本不会变化的部分。

    @Select({"select", select_field, "from", table_name})
    List<Book> selectAll();

    @Insert({"insert into", table_name, "(", insert_field, ") values (#{name},#{author},#{price})"})
    int addBook(Book book);

    @Update({"update ", table_name, " set status=#{status} where id=#{id}"})
    void updateBookStatus(@Param("id") int id, @Param("status") int status);

    @Select({"select", select_field, "from", table_name ," where name=#{name}"})
    Book selectBookByName(String name);

    @Select({"select", select_field, "from", table_name ," where id=#{id}"})
    Book selectBookById(int id);

}
