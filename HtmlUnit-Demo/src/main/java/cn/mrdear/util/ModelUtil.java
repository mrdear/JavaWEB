package cn.mrdear.util;

import java.util.Random;

/**
 * @author Niu Li
 * @date 2016/10/8
 */
public class ModelUtil {

    private static final String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V" ,
            "W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};

    //B7-24-D6-39-59-BC-B2-AE-E1-B2-B3-64-24-60-ED-C0
    public static String generateId(){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            if (i != 15){
                builder.append(letters[random.nextInt(letters.length)]).append(letters[random.nextInt(letters.length)]).append("-");
            }else {
                builder.append(letters[random.nextInt(letters.length)]).append(letters[random.nextInt(letters.length)]);
            }
        }
        return builder.toString();
    }
}
