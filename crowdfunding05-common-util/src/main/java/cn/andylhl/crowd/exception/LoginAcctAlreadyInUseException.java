package cn.andylhl.crowd.exception;

/***
 * @Title: LoginAcctAlreadyInUseException
 * @Description: 保存或更新时，登录账号重复抛出这个异常
 * @author: lhl
 * @date: 2020/12/25 15:28
 */
public class LoginAcctAlreadyInUseException extends Exception {

    public LoginAcctAlreadyInUseException() {
        super();
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }
}
