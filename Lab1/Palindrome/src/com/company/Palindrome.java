package com.company;
// Класс, который проверяет является ли строка полиндромом
public class Palindrome {
    public static void main(String[] args) {
        for (int i=0; i<args.length; i++){
            String s = args[i];
            if (isPalindrome(s)){
                System.out.println(s + "- палиндром");
            }
            else {
                System.out.println(s + "- не палиндром");
            }
        }
    }
    // Метод, который позволяет изменить символы в строке
    public static String reverseString(String s){
        String revSTR = "";
        for (int i = s.length()-1; i >= 0; i--) {
            revSTR += s.charAt(i);
        }
        return revSTR;
    }
    // Метод, который переворачивает слово и сравнивает с изначальным
    public static boolean isPalindrome(String s){
       if(s.equals(reverseString(s))){
           return true;
       }
       else {
           return false;
       }
    }
}
