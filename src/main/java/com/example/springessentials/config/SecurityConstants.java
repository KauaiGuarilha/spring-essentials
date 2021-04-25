package com.example.springessentials.config;

public class SecurityConstants {
    static final String SECRET = "EssentialsSecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/users/sign-up";
    static final long EXPIRATION_TIME = 86400000L; // 1 Day in milliseconds

    //    public static void main(String[] args){
    //        System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    //    }
}
