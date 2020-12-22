package cn.andylhl.crowd.entity;

/***
 * @Title: Student
 * @Description:
 * @author: lhl
 * @date: 2020/12/22 10:28
 */
public class TestStudent {

    private String id;
    private String name;
    private TestClassGrade classgrade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestClassGrade getClassgrade() {
        return classgrade;
    }

    public void setClassgrade(TestClassGrade classgrade) {
        this.classgrade = classgrade;
    }

    @Override
    public String toString() {
        return "TestStudent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", classgrade=" + classgrade +
                '}';
    }
}
