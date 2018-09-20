package com.simpu;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Session
{
    private String name;
    private String code;
    private Console console;
    private Format format;
    private Listener listener;
    private ArrayList<String> userList;

    private final String websiteURL = "http://www.cross-copy.net/api/";

    public Session()
    {
        // Initialise User List
        userList = new ArrayList<String>();
        // Initialise the console
        console = new Console();

        // Get Name as input
        console.PrintTabbedSameLine("Enter username :");
        name = console.GetInput();

        // Get Code as input
        console.PrintTabbedSameLine("Enter code :");
        code = console.GetInput();

        // Get key and initialise Format
        console.PrintTabbedSameLine("Enter secret key :");
        format = new Format(console.GetInput().trim());

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

        // Get First Input
        console.PrintReply();

        // Input Loops
        while (true)
        {
            String message = console.GetInput();
            Sender sender = new Sender(format.Encode(format.Message(name,message)),websiteURL+code);
            sender.start();
            console.PrintReplySingleLine();
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
            oStreamWriter.write(format.Encode(format.UserAnnounce(name)));
            oStreamWriter.close();
            connection.getInputStream();
        }
        catch (Exception e)
        {
            console.PrintError(e.toString());
            console.PrintReply();
            return false;
        }
        return true;
    }
    public void AddUser(String string)
    {
        for (int i = 0;i < userList.size();i++)
        {
            if (userList.get(i).equals(string))
            {
                return;
            }
        }
        console.PrintSystem("User "+string+" is Online");
        userList.add(string);
    }

    public void ProcessString(String string)
    {

        string = format.Decode(string);
        StringBuilder stringBuilder = new StringBuilder();
        if (string.startsWith(Format.headerUserMessage))
        {
            String[] s = string.split(":",3);
            if (!s[1].equals(name))
            {
                stringBuilder.append("\t@");
                stringBuilder.append(s[1]);
                stringBuilder.append(" : ");
                stringBuilder.append(s[2]);
                console.PrintTabbed(stringBuilder.toString());
                console.PrintReply();
            }
        }
        if (string.startsWith(Format.headerUserAnnounce))
        {
            String[] s = string.split(":",2);
            if (!s[1].equals(name))
            {
                AddUser(s[1]);
                console.PrintReply();
                StringBuilder data = new StringBuilder();
                data.append(name);
                for (int i = 0;i < userList.size();i++)
                {
                    data.append(":");
                    data.append(userList.get(i));
                }
                Sender sender = new Sender(format.Encode(format.UserCallback(data.toString())), websiteURL + code);
                sender.start();
            }
        }
        if (string.startsWith(Format.headerUserCallback))
        {
            String[] s = string.split(":");
            for (int i = 1;i < s.length;i++)
            {
                if (!s[i].equals(name))
                {
                    AddUser(s[i]);
                }
            }
            console.PrintReply();
        }
    }
}