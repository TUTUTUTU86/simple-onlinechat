package com.chat.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class InviteCodeGenerator {

    public static String generateInviteCode(long seed){
        ThreadLocalRandom randomizer = ThreadLocalRandom.current();
        randomizer.setSeed(seed);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 15 + randomizer.nextInt(20); i++)
            stringBuilder.append(randomizer.nextInt(255));
        return stringBuilder.toString();
    }

    public static String generateInviteCode(){
        ThreadLocalRandom randomizer = ThreadLocalRandom.current();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 15 + randomizer.nextInt(20); i++)
            stringBuilder.append((char)(randomizer.nextInt(50) + 65));
        return stringBuilder.toString();
    }
}
