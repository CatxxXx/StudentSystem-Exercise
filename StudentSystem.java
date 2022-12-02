package com.hudson.studentsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    public static void startStudentSystem() {

        //creat Student List
        ArrayList<Student> studentList = new ArrayList<>();

        //main menu
        loop:
        while (true) {
            System.out.println("---------------Welcom to Student System---------------");
            System.out.println("1,Add");
            System.out.println("2,Delet");
            System.out.println("3,Edit");
            System.out.println("4,Query");
            System.out.println("5,Exit");
            System.out.println("Choose(1 - 5):");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();
            switch (choose) {
                case "1" -> addStudent(studentList);
                case "2" -> deletStudent(studentList);
                case "3" -> editStudent(studentList);
                case "4" -> queryStudent(studentList);
                case "5" -> {
                    System.out.println("Exit");
                    break loop;
                }
                default -> System.out.println("Not right choice");
            }
        }
    }

    /**
     * student info add
     * @param studentList
     * @return
     */
    public static ArrayList<Student> addStudent(ArrayList<Student> studentList) {
        Scanner sc = new Scanner(System.in);
        Student student = new Student();
        //id check & set
        while (true) {
            System.out.println("Id:");
            String id = sc.next();
            boolean flag = contains(studentList, id);
            if (flag) {
                System.out.println("Id is be used, please try other id.");
            } else {
                student.setId(id);
                break;
            }
        }
        //type in info
        System.out.println("Name:");
        String name = sc.next();
        student.setName(name);

        System.out.println("Age:");
        int age = sc.nextInt();
        student.setAge(age);

        System.out.println("Address:");
        String address = sc.next();
        student.setAddress(address);

        studentList.add(student);
        System.out.println("Successfully added.");
        return studentList;

    }


    /**
     * student info delet
     * @param studentList
     */
    public static void deletStudent(ArrayList<Student> studentList) {
        //id get
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the student id you want to delete.");
        String id = sc.next();
        //get this id's index
        int index = getIndex(studentList, id);
        if(index >= 0) {
            studentList.remove(index);
            System.out.println("Successfully deleted.");
        } else {
            System.out.println("The id does not exist.");
        }
    }


    /**
     * student info edit
     * @param studentList
     */
    public static void editStudent(ArrayList<Student> studentList) {
        //id get
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the student id you want to edit.");
        String id = sc.next();
        //get this id's index
        int index = getIndex(studentList, id);
        if(index == -1) {
            System.out.println("The id does not exist.");
            return;
        }

        //edit info
        Student student = studentList.get(index);
        System.out.println("Name:");
        String newName = sc.next();
        student.setName(newName);

        System.out.println("Age:");
        int newAge = sc.nextInt();
        student.setAge(newAge);

        System.out.println("Address:");
        String newAddress = sc.next();
        student.setAddress(newAddress);

        System.out.println("Successfully edited.");
    }


    /**
     * student info query
     * @param studentList
     */
    public static void queryStudent(ArrayList<Student> studentList) {
        //cannot find id
        if (studentList.size() == 0) {
            System.out.println("Cannot get info,try again please.");
            return;
        }

        //info get
        System.out.println("id\t\tname\tage\taddress");
        for (int i = 0; i < studentList.size(); i++) {
            System.out.println(studentList.get(i).getId() + "\t\t" +
                    studentList.get(i).getName() + "\t" +
                    studentList.get(i).getAge() + "\t" +
                    studentList.get(i).getAddress());
        }

    }

    /**
     * id contain
     * @param studentList
     * @param id
     * @return
     */
    public static boolean contains(ArrayList<Student> studentList, String id) {
        /*for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getId().equals(id)) {
                return true;
            }
        }*/

        return getIndex(studentList, id) >= 0;
    }

    /**
     * id's index get
     * @param studentList
     * @param id
     * @return
     */
    public static int getIndex (ArrayList<Student> studentList, String id) {
        //get this id's index
        for (int i = 0; i < studentList.size(); i++) {
            if(studentList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
