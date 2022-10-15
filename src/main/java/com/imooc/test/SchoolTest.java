package com.imooc.test;

import com.imooc.model.Student;
import com.imooc.model.Subject;

public class SchoolTest {
    public static void main(String[] args) {
        // test subject
        Subject sub1 = new Subject("computer","J0001",4);
        System.out.println(sub1.info());
        System.out.println("======================");
        // test student
        Student stu1 = new Student("S01","zhangsan","boy",18);
        System.out.println(stu1.introduction());

    }
}
