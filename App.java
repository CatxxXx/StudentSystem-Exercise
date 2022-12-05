package com.hudson.studentsystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {
    private static final String LOGIN = "1";
    private static final String REGIST = "2";
    private static final String FORGET_PASSWORD = "3";
    private static final String EXIT = "4";
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<User> userList = new ArrayList<>();
        while (true) {
            System.out.println("---------------Welcom to Student System---------------");
            System.out.println("1,Login");
            System.out.println("2,Regist");
            System.out.println("3,Forget password");
            System.out.println("4,Exit");
            System.out.println("Choose(1 - 4):");
            String choose = sc.next();
            switch (choose) {
                case LOGIN -> userLogin(userList);
                case REGIST -> userRegist(userList);
                case FORGET_PASSWORD -> passWordFoget(userList);
                case EXIT -> {
                    System.out.println("Thank you, goodbye.");
                    System.exit(0);
                }
                default -> System.out.println("Not right choice");
            }
        }
    }

    /**
     * user login
     * @param userList
     */
    private static void userLogin(ArrayList<User> userList) {
        Scanner sc = new Scanner(System.in);
        //is username exist check
        for (int i = 0; i < 3; i++ ) {
            System.out.println("Username:");
            String userName = sc.next();
            boolean flag = userNameContain(userList, userName);
            if(!flag) {
                System.out.println("Username is not exist. Please regist your account.");
                return;
            }
            System.out.println("Password:");
            String passWord = sc.next();

            //verificationcode input & check
            while (true) {
                String rightCode = getVerificationCode();
                System.out.println("VerificationCode: " + rightCode);
                System.out.print("Enter the code:");
                String inputCode = sc.next();
                if(!(inputCode.equalsIgnoreCase(rightCode))) {
                    System.out.println("Code is incorrect, please try again.");
                    continue;
                } else {
                    System.out.println("CODE CORRECT!");
                    break;
                }
            }

            //username & password check (3 chances)
            User userLogin = new User(userName, passWord, null, null);
            boolean userLoginFlag = userLoginCheck(userList, userLogin);
            if(userLoginFlag) {
                System.out.println("Successfully login.");
                StudentSystem ss = new StudentSystem();
                ss.startStudentSystem();
                break;
            } else {
                System.out.println("User name or password is incorrect.");
                if(i == 2) {
                    System.out.println("This account " + userName + " is locked.");
                    return;
                } else {
                    System.out.println("Still have " + (2 - i) + " chance.");
                }
            }
        }
    }

    /**
     * user regist
     *
     * @param userList
     */
    private static void userRegist(ArrayList<User> userList) {
        Scanner sc = new Scanner(System.in);
        //username set
        String userName;
        while (true) {
            System.out.println("Enter the username:");
            userName = sc.next();
            boolean userNameFlag = userNameCheck(userList, userName);
            if (!userNameFlag) {
                continue;
            }
            if (!userNameContain(userList, userName)) {
                System.out.println("Username successfully set.");
                break;
            } else {
                System.out.println("Username is used. Please try other name.");
                continue;
            }
        }

        //password set
        String passWord;
        while (true) {
            System.out.println("Enter your password:");
            passWord = sc.next();
            System.out.println("Enter your password again:");
            String passwordCheck = sc.next();
            if (!passwordCheck.equals(passWord)) {
                System.out.println("The two passwords are inconsistent, please try again.");
                continue;
            } else {
                System.out.println("Password successfully set.");
                break;
            }
        }

        //userid set
        String userId;
        while (true) {
            System.out.println("Enter your id:");
            userId = sc.next();
            boolean userIdFlag = userIdCheck(userId);
            if (userIdFlag) {
                System.out.println("Userid successfully set.");
                break;
            } else {
                System.out.println("Id is not standard.");
                continue;
            }
        }

        //phone number set
        String phoneNumber;
        while (true) {
            System.out.println("Enter your phone number:");
            phoneNumber = sc.next();
            boolean phoneNumberFlag = phoneNumberCheck(phoneNumber);
            if (phoneNumberFlag) {
                System.out.println("Phone number successfully set.");
                break;
            } else {
                System.out.println("Your phone number is incorrect.");
                continue;
            }
        }

        User user = new User(userName, passWord, userId, phoneNumber);
        userList.add(user);
        System.out.println("Congratulations, your account is successfully registed.");

    }

    /**
     * password forget & reset
     * @param userList
     */
    public static void passWordFoget(ArrayList<User> userList) {
        Scanner sc = new Scanner(System.in);
        //is username exist check
        System.out.println("Enter the username:");
        String userName = sc.next();
        boolean flag = userNameContain(userList, userName);
        if(!flag) {
            System.out.println("Username is not exist.");
            return;
        }
        //is userid & phone number correct check
        System.out.println("Enter your user id:");
        String userId = sc.next();
        System.out.println("Enter your phone number:");
        String phoneNumber = sc.next();
        int index = findeIndex(userList, userName);
        User user = userList.get(index);
        if(!(user.getUserId().equals(userId) && user.getPhoneNumber().equals(phoneNumber))) {
            System.out.println("User id or phone number is not correct.");
            return;
        }
        //new password reset
        while (true) {
            System.out.println("Enter your new password:");
            String passWord = sc.next();
            System.out.println("Enter your new password again:");
            String passwordCheck = sc.next();
            if (!passwordCheck.equals(passWord)) {
                System.out.println("The two passwords are inconsistent, please try again.");
                continue;
            } else {
                user.setPassWord(passWord);
                System.out.println("Password successfully reset.");
                break;
            }
        }
    }

    /**
     * find username's index
     * @param userList
     * @param userName
     * @return
     */
    private static int findeIndex(ArrayList<User> userList, String userName) {
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUserName().equals(userName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * username standard check
     *
     * @param userList
     * @param userName
     * @return is username correct
     */
    public static boolean userNameCheck(ArrayList<User> userList, String userName) {
        int count = 0;
        int len = userName.length();
        //username length check
        if (len > 15) {
            System.out.println("Username too long.");
            return false;
        } else if (len < 3) {
            System.out.println("Username too short");
            return false;
        }
        //username standard check
        for (int i = 0; i < len; i++) {
            char c = userName.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                System.out.println("Username is not standardized.");
                return false;
            }
        }
        for (int i = 0; i < len; i++) {
            char c = userName.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
                return true;
            }
        }
        System.out.println("Username needs to be composed of letters and numbers.");
        return false;

    }


    /**
     * username be used check
     *
     * @param userList
     * @param userName
     * @return is username be used
     */
    public static boolean userNameContain(ArrayList<User> userList, String userName) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * userid check
     *
     * @param userId
     * @return is userid correct
     */
    public static boolean userIdCheck(String userId) {
        //userid length check
        if (userId.length() != 18) {
            return false;
        }
        //userid first number check
        if (userId.startsWith("0")) {
            return false;
        }
        //userid all number check (except last index)
        for (int i = 0; i < userId.length() - 1; i++) {
            char c = userId.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        //userid last index check
        char endIndex = userId.charAt(userId.length() - 1);
        if ((endIndex >= '0' && endIndex <= '9') || endIndex == 'x' || endIndex == 'X') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * phone number check
     *
     * @param phoneNumber
     * @return is phone number correct
     */
    public static boolean phoneNumberCheck(String phoneNumber) {
        //phone number length and first number check
        if (phoneNumber.length() != 10 || phoneNumber.startsWith("0")) {
            return false;
        }
        //phone number all number check
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;

    }

    /**
     * get verificationcode
     * @return VerificationCode
     */
    public static String getVerificationCode () {
        ArrayList<Character> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a' + i));
            list.add((char)('A' + i));
        }
        Random rd = new Random();
        for (int i = 0; i < 4; i++) {
            int index = rd.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }
        sb.append(rd.nextInt(10));
        char[] arr = sb.toString().toCharArray();
        int randomIndex = rd.nextInt(arr.length);
        char temp = arr[randomIndex];
        arr[randomIndex] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;
        return new String(arr);
    }

    /**
     * username & password check
     * @param userList
     * @param user
     * @return
     */
    public static boolean userLoginCheck(ArrayList<User> userList, User user) {
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUserName().equals(user.getUserName()) && userList.get(i).getPassWord().equals(user.getPassWord())){
                return true;
            }
        }
        return false;
    }
}
