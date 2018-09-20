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
        System.out.println("# "+string);
    }
    public void PrintError(String string)
    {
        System.out.println("!(ERROR) "+string);
    }
    public void PrintTabbed(String string)
    {
        System.out.println('\t'+string);
    }
    public void PrintTabbedSameLine(String string)
    {
        System.out.print('\t'+string);
    }
    public void PrintDebug(String s)
    {
        System.out.println("<DEBUG>"+s);
    }
    public void Print(String s)
    {
        System.out.println(s);
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
