package com.simpu;

import java.util.Scanner;

public class Console
{
    private Scanner scanner;
    public Console()
    {
        scanner = new Scanner(System.in);
    }
    public void PrintSystem(String string)
    {
        System.out.println();
        System.out.print("# "+string);
    }
    public void PrintError(String string)
    {
        System.out.println();
        System.out.print("!(ERROR) "+string);
    }
    public void PrintTabbed(String string)
    {
        System.out.println();
        System.out.print('\t'+string);
    }
    public void PrintTabbedSameLine(String string)
    {
        System.out.print('\t'+string);
    }
    public void PrintDebug(String s)
    {
        System.out.println();
        System.out.print("<DEBUG>"+s);
    }
    public void Print(String s)
    {
        System.out.println();
        System.out.print(s);
    }
    public void PrintLine()
    {
        System.out.println();
    }
    public void PrintNotLine(String s)
    {
        System.out.print(s);
    }
    public void PrintReply()
    {
        System.out.println();
        System.out.print("\tReply --> ");
    }
    public void PrintReplySingleLine()
    {
        System.out.print("\tReply --> ");
    }

    public String GetInput()
    {
        String string = null;
        while (string == null || string.trim().length() == 0)
        {
            string = scanner.nextLine();
        }
        return string;
    }

    public void Dispose()
    {
        scanner.close();
    }
}
