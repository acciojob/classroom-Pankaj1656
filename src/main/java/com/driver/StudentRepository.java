package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Repository
public class StudentRepository {
    private  List<Student> studenData=new ArrayList<>();
    private  List<Teacher> teacherData=new ArrayList<>();
    private HashMap<Teacher,List<Student>> teacherStudentMap=new HashMap<>();

    public void addStudent(Student student) {
        studenData.add(student);

    }

    public void addTeacher(Teacher teacher) {

        teacherData.add(teacher);
    }

    public void addStudentTeacherPair(String student, String teacher) {
        Teacher teacher1=getTeacherByName(teacher);
        Student student1=getStudentByname(student);
        List<Student> list=teacherStudentMap.getOrDefault(teacher1,new ArrayList<>());
        list.add(student1);
        teacherStudentMap.put(teacher1,list);
    }

    public Teacher getTeacherByName(String teacher) {
        for(Teacher teacher1:teacherData){
            if(teacher1.getName().equals(teacher)){
                return teacher1;
            }
        }
        throw  new RuntimeException("Teacher not found");
    }

    public Student getStudentByname(String name) {
        for(Student student:studenData){
            if(student.getName().equals(name)){
                return student;
            }
        }
        throw new RuntimeException("Student not found");
    }

    public List<String> getStudentByTeacherName(String teacher) {
        List<String> listOfStudents=new ArrayList<>();
        Teacher teacher1=getTeacherByName(teacher);
        List<Student> studentList=teacherStudentMap.getOrDefault(teacher1,new ArrayList<>());
        for(Student student:studentList){
            listOfStudents.add(student.getName());
        }
        return listOfStudents;
    }

    public List<String> getAllStudents() {
        List<String> allStudents=new ArrayList<>();
        for(Student student : studenData){
            allStudents.add(student.getName());
        }
        return allStudents;
    }


    public void deleteTeacherByName(String teacher) {
        Teacher teacher1=getTeacherByName(teacher);
        List<Student> studentsOfTeacher=teacherStudentMap.getOrDefault(teacher1,new ArrayList<>());
        for(Student students: studentsOfTeacher){
            studenData.remove(students);
        }
        teacherStudentMap.remove(teacher1);
        teacherData.remove(teacher1);
    }

    public void deleteAllTeachers() {
        for(Teacher teacher:teacherData){
            deleteTeacherByName(teacher.getName());
            teacherStudentMap.remove(teacher);
            teacherData.remove(teacher);
        }

    }
}
