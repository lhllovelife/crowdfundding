package cn.andylhl.crowd.entity;

/***
 * @Title: classgrade
 * @Description:
 * @author: lhl
 * @date: 2020/12/22 10:29
 */
public class TestClassGrade {

    private String classno;
    private String classname;

    public String getClassno() {
        return classno;
    }

    public void setClassno(String classno) {
        this.classno = classno;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public String toString() {
        return "TestClassGrade{" +
                "classno='" + classno + '\'' +
                ", classname='" + classname + '\'' +
                '}';
    }
}
