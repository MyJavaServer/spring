package com.jayyin.spring.jdbctest.model;

/**
 * @author jerryyin
 * @version 1.0
 * @name Student
 * @description TODO
 * @date 2019/5/7 15:32
 **/
public class Student {

    /**
     * id : 1
     * name : jay
     * age : 24
     */

    private Integer id;
    private String name;
    private Integer age;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
