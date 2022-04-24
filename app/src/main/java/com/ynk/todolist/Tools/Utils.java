package com.ynk.todolist.Tools;

import java.util.Random;

public class Utils {

    public static final String APP_NAME = "TODOLIST";

    //Login Utils
    public static final String loginControlKey = "isLogin";
    public static final String loginUserNameKey = "userName";
    public static final String loginUserPassword = "userPassword";

    public static final String todoListFragmentTag = "todolist";

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static String getRandomString(final int sizeOfPasswordString){
          final Random random = new Random();
          final StringBuilder stringBuilder = new StringBuilder(sizeOfPasswordString);

          for(int i=0; i< 6; ++i){
            stringBuilder.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
          }
          return stringBuilder.toString();
    }
}
