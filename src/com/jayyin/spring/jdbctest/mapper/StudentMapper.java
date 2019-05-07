package com.jayyin.spring.jdbctest.mapper;

import com.jayyin.spring.jdbctest.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jerryyin
 * @version 1.0
 * @name StudentMapper
 * @description TODO
 * @date 2019/5/7 15:35
 **/
public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setAge(resultSet.getInt("age"));
        return student;
    }

}
