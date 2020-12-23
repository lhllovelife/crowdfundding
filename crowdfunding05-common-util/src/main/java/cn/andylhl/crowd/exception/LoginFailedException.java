package cn.andylhl.crowd.exception;

/***
 * @Title: LoginFailedException
 * @Description: 登录失败异常
 * @author: lhl
 * @date: 2020/12/23 16:30
 */
public class LoginFailedException extends Exception {

    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
