package cn.andylhl.crowd.constant;

/***
 * @Title: Const
 * @Description: 项目中用到的常量
 * @author: lhl
 * @date: 2020/12/20 16:23
 */
public class Constant {

    private Constant(){}


    // 日期全格式
    public static final String DATE_Format_ALL = "yyyy-MM-dd HH:mm:ss";

    // 年月日格式
    public static final String DATE_Format_YMD = "yyyy-MM-dd";


    public static final String MESSAGE_LOGIN_FAILED = "抱歉！账号密码错误！请重新输入！";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！这个账号已经被使用了！";
    public static final String MESSAGE_ACCESS_FORBIDDEN = "请登录以后再访问！";
    public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法！请不要传入空字符串！";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误：登录账号不唯一！";
    public static final String MESSAGE_HEADPIC_UPLOAD_FAILE = "头图上传失败！请重新填写项目信息后提交";
    public static final String MESSAGE_HEADPIC_IS_EMPTY = "头图不存在！请重新填写项目信息后提交";
    public static final String MESSAGE_DETAILPIC_IS_EMPTY = "详情图片不存在！请重新填写项目信息后提交";
    public static final String MESSAGE_DETAILPIC_UPLOAD_FAILE = "详情图片上传失败！请重新填写项目信息后提交";
    public static final String MESSAGE_RETURNPIC_IS_EMPTY = "图片为空，请重新上传";
    public static final String MESSAGE_TEMPLE_PROJECTVO_MISS = "临时存储的ProjectVO对象丢失";

    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String ATTR_ERROR_MESSAGE = "message";
    public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
    public static final String ATTR_NAME_TEMPLE_PROJECT = "templeProjectVO";
    public static final String ATTR_NAME_PORTAL_DATA = "portalTypeVOList";

    public static final String REDIS_CODE_PREFIX = "crowd-register-";
}
