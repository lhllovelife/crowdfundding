package cn.andylhl.crowd.exception;

/***
 * @Title: LoginAcctAlreadyInUseForUpdateException
 * @Description: 更新用户信息保存时，登录账号重复抛出这个异常
 * @author: lhl
 * @date: 2020/12/25 20:53
 */
public class LoginAcctAlreadyInUseForUpdateException extends Exception{

    public LoginAcctAlreadyInUseForUpdateException() {
        super();
    }

    public LoginAcctAlreadyInUseForUpdateException(String message) {
        super(message);
    }
}
