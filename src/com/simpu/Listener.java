package com.simpu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Listener extends Thread
{
    private String stringURL;
    private final String websiteURL = "http://www.cross-copy.net/api/";
    private boolean close = false;
    private Console console;
    private Session session;

    public Listener(String code,Console console,Session s)
    {
        stringURL = websiteURL+code;
        this.console = console;
        this.session = s;
    }

    public void Process(String string)
    {
        session.ProcessString(string);
    }

    public void run()
    {
        while (!close)
        {
            try
            {
                URL url = new URL(stringURL);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setConnectTimeout(0);
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK)
                {
                    console.PrintDebug(responseCode + "");
                    continue;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String in;
                StringBuffer output = new StringBuffer();
                while ((in = reader.readLine()) != null)
                {
                    output.append(in);
                }
                reader.close();
                Process(output.toString());
            }
            catch (Exception e)
            {
                console.PrintError(e.toString());
            }
        }
    }
}
