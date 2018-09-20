package com.simpu;

import java.util.Scanner;

public class Format
{
    public static String headerUserAnnounce = "newuserconnected";
    public static String headerUserCallback = "calluserconnected";
    public static String headerUserMessage = "message";
    private String key;
    private boolean encrypt = false;

    public Format(String s)
    {
        key =s;
    }
    public String UserAnnounce(String username)
    {
        return headerUserAnnounce+":"+username;
    }
    public String UserCallback(String username)
    {
        return  headerUserCallback+":"+username;
    }
    public String Decode(String string)
    {
        if (encrypt)
        {
            char[] c = string.toCharArray();
            for (int i = 0; i < string.length(); i++)
            {
                c[i] = (char) (((int) c[i] - (int) key.charAt(i % key.length()) + 256) % 256);
            }
            string = new String(c);
        }
        return string;
    }
    public String Encode(String string)
    {
        if (encrypt)
        {
            char[] c = string.toCharArray();
            for (int i = 0; i < string.length(); i++)
            {
                c[i] = (char) (((int) c[i] + (int) key.charAt(i % key.length())) % 256);
            }
            string = new String(c);
        }
        return string;
    }
    public String Message(String name,String message)
    {
        return (headerUserMessage+":"+name+":"+message);
    }
}
