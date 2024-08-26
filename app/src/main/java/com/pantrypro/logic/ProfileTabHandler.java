package com.pantrypro.logic;

public class ProfileTabHandler {

    public static String maskedPassword(String str){
        StringBuilder maskedString = new StringBuilder();
        if(str == null){
            return "********";
        }
        for (int i = 0; i < str.length() - 3; i++) {
            maskedString.append("*");
        }
        maskedString.append(str.substring(str.length() - 3));
        return maskedString.toString();
    }

    public static boolean isGuestUser(String username, String password){
        return username.equals("Guest") && password.equals("N/A");
    }
}
