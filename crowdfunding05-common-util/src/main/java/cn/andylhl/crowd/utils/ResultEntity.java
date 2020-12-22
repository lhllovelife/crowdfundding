package cn.andylhl.crowd.utils;

/***
 * @Title: ResultEntity
 * @Description: 统一项目ajax返回结果的值
 * @author: lhl
 * @date: 2020/12/22 11:34
 */
public class ResultEntity<T> {

    //常量
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    //用来封装当前请求处理的结果，是成功还是失败
    private String result;
    //请求处理失败返回的错误信息
    private String message;
    //要返回的数据
    private T data;


    /**
     * 请求处理成功且不需要返回数据
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData(){
        return new ResultEntity<>(SUCCESS, null, null);
    }

    /**
     * 请求处理成功且需要返回数据
     * @param data 要返回的数据
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data){
        return new ResultEntity<Type>(SUCCESS, null, data);
    }

    /**
     * 请求处理失败使用的工具类方法
     * @param message 失败的错误信息
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String message){
        return new ResultEntity<Type>(FAILED, message, null);
    }



    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getFAILED() {
        return FAILED;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
