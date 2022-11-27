package BookManager.project.model.enums;
/**
 *  书本的状态枚举
 */

public enum BookStatusEnum {

    NORMAL(0),  //正常
    DELETE(1),  //删除
    RECOMMENDED(2), //推荐
    ;

    private int value;


    /**
     * 修改书本的状态，以输入0,1,2；返回显示NORMAL...
     */
    BookStatusEnum(int value){
        this.value = value;
    }

    /**
     * 返回书的状态
     */
    public int getValue(){
        return value;
    }

}
