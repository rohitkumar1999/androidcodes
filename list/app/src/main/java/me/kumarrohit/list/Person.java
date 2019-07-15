package me.kumarrohit.list;

public class Person {

    String name ;
    String dob ;
    String sex ;

    public Person(String name, String dob, String sex) {
        this.name = name;
        this.dob = dob;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
