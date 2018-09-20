package com.simpu;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Session
{
    private String name;
    private String code;
    private Console console;
    private Format format;
    private Listener listener;

    private final String websiteURL = "http://www.cross-copy.net/api/";

    public Session()
    {
        // Initialise the console
        console = new Console();

        // Initialise the formater
        format = new Format();

        // Get Name as input
        console.PrintTabbedSameLine("Enter username :");
        name = console.GetInput();

        // Get Code as input
        console.PrintTabbedSameLine("Enter code :");
        code = console.GetInput();

        // Display the action parameters
        console.PrintSystem("Connecting Room : "+code+"\t as alias : "+name);

        // Announce and test connection
        if (!AnnounceUserName())
        {
            console.PrintError("Unable to connect www.cross-copy.net");
            return;
        }
        else
        {
            console.PrintSystem("Connection OK");
        }
        // Start the Listener
        listener = new Listener(code,console,this);
        listener.start();

        while (true)
        {
            console.PrintNotLine("Reply-->");
            String message = console.GetInput();
            Sender sender = new Sender(format.Message(name,message),websiteURL+code);
            sender.start();
        }
    }

    public void Dispose()
    {
        console.Dispose();
    }

    public boolean AnnounceUserName()
    {
        try
        {
            URL url = new URL(websiteURL+code);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            OutputStreamWriter oStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            oStreamWriter.write(format.UserAnnounce(name));
            oStreamWriter.close();
            connection.getInputStream();
        }
        catch (Exception e)
        {
            console.PrintError(e.toString());
            return false;
        }
        return true;
    }

    public void ProcessString(String string)
    {
        String[] s = string.split(":",3);
        StringBuilder stringBuilder = new StringBuilder();
        if (s[0].equals(Format.headerUserMessage))
        {
            if (!s[1].equals(name))
            {
                stringBuilder.append("@");
                stringBuilder.append(s[1]);
                stringBuilder.append(" : ");
                stringBuilder.append(s[2]);
                console.PrintLine();
                console.Print(stringBuilder.toString());
                console.PrintNotLine("Reply-->");
            }
        }
    }
}