package com.simpu;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Sender extends Thread
{
    public String string;
    public String stringUrl;

    public static ArrayList<Sender> senderList = new ArrayList<Sender>();

    public Sender(String string,String url)
    {
        this.string = string;
        this.stringUrl = url;
        senderList.add(this);
    }

    public void run()
    {
        boolean exit = false;
        while (!exit)
        {
            try
            {
                URL url = new URL(stringUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setConnectTimeout(5000);
                connection.setDoOutput(true);
                OutputStreamWriter oStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                oStreamWriter.write(string);
                oStreamWriter.close();
                connection.getInputStream();
                exit = true;
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
        senderList.remove(this);
    }
}
