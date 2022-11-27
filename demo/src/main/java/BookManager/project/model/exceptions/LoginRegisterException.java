package BookManager.project.model.exceptions;


/**
 * 登录与注册时的异常处理。这时需要导向前端的弹窗
 * 因此需要专门处理
 */

public class LoginRegisterException extends RuntimeException{
    public LoginRegisterException() {
        super();
    }

    public LoginRegisterException(String message) {
        super(message);
    }

    public LoginRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginRegisterException(Throwable cause) {
        super(cause);
    }
}
