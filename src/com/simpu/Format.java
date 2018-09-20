package com.simpu;

public class Format
{
    public static String headerUserAnnounce = "newuserconnected";
    public static String headerUserMessage = "message";
    public String UserAnnounce(String username)
    {
        return headerUserAnnounce+":"+username;
    }
    public String Decode(String string)
    {
        return string;
    }
    public String Encode(String string)
    {
        return string;
    }
    public String Message(String name,String message)
    {
        return (headerUserMessage+":"+name+":"+message);
    }
}
