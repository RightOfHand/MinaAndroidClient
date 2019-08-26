package com.songy.drawdemo;

/**
 * Description:
 *
 * @author by song on 2019-08-07.
 * email：bjay20080613@qq.com
 */
public class FizzBuzz {

    public static  String get(int arg){
        if (arg%3==0 && arg%5==0){
            return "fizzbuss";
        }else if (arg%3==0){
            return "fizz";
        }else if (arg%5==0){
            return "buss";
        }
        return String.valueOf(arg);
    }
}
