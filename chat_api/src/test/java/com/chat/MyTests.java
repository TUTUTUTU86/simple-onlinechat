package com.chat;

public class MyTests {
    static class SomeClass{
        public int userID;
        SomeClass(long i){
            userID = (int)i;
        }
    }

    public static void main(String args[]){
        Object object = new SomeClass(123);
        class SomeAnonClass{
            public int userID;
        }
        SomeAnonClass someAnonClass = (SomeAnonClass) object;
        System.out.println(someAnonClass.userID);
    }
}
