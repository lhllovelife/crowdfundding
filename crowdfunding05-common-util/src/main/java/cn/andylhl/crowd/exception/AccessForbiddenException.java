package cn.andylhl.crowd.exception;

/***
 * @Title: AccessForbiddenException
 * @Description: 访问受限资源
 * @author: lhl
 * @date: 2020/12/23 20:58
 */
public class AccessForbiddenException extends Exception {

    public AccessForbiddenException() {
        super();
    }

    public AccessForbiddenException(String message) {
        super(message);
    }
}
