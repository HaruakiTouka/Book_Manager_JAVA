package BookManager.project.model;

//书本的类
public class Book {
    private int id;

    private String name;

    private String author;

    private String price;


    /**
     * 连接BookStatusEnum这一枚举类：用于确定书的状态
     *     NORMAL(0),  //正常
     *     DELETE(1),  //删除
     *     RECOMMENDED(2), //推荐
     * {@link BookManager.project.model.enums.BookStatusEnum}
     */
    private int status;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



}

