package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class EmployeeBook {

    //Declare the hashmap to hold the employee data
    static HashMap<String, List<Double>> employee = new HashMap<>();

    public static void main(String[] args) throws IOException {

        double empnum;
        double annsal;
        String empname = "";
        boolean flag = true;

        Scanner in = new Scanner(System.in);

        TransferData();

        while(!(empname.equals("ex")))
        {
            System.out.println("Enter the Employee name(Type ex to quit): ");
            empname = in.nextLine();
            if(empname.equals("ex"))
            {
                break;
            }
            System.out.println("Enter Employee Number: ");
            empnum = in.nextDouble();
            flag = true;

            while(flag)
            {
                try{
                    System.out.println("Enter Employee Salary: ");
                    annsal = in.nextDouble();
                    if(annsal < 0 || annsal == 0)
                    {
                        throw new MyError("Salary should be more than 0");
                    }
                    flag = false;
                    employee.put(empname, new ArrayList<>(Arrays.asList(empnum,annsal)));
                    in.nextLine();
                }
                catch (InputMismatchException a)
                {
                    System.out.println("You should enter a number!!");
                    in.nextLine();
                }
                catch (MyError m)
                {
                    System.out.println(m.getMessage());
                }
            }
        }

        Display();
        SaveFile();

        System.out.println("Data is saved to text file!");
    }

    public static void TransferData()
    {
        //Creating a file object
        File tempfile = new File("MyEmployeeList.txt");
        //check if file exists
        boolean exist = tempfile.exists();

        String input[];
        //put data that we read from the file
        try {
            if (exist) {
                Scanner ac = new Scanner(tempfile);

                while(ac.hasNextLine())
                {
                    String data = ac.nextLine();
                    input = data.split("\\s");
                    double empn = Double.parseDouble(input[1]);
                    double emps = Double.parseDouble(input[2]);
                    employee.put(input[0], new ArrayList<>(Arrays.asList(empn,emps)));                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("The file does not exist");
        }
    }

    public static void Display()
    {
        int count = employee.size();

        System.out.println("There are currently " + count + " of employees in your records \n");

        for(Map.Entry<String, List<Double>> entry: employee.entrySet())
        {
            System.out.print("Employee Name: " + "\t\t" + entry.getKey() + "\n");
            System.out.print("Employee Number: " + "\t" + employee.get(entry.getKey()).get(0) + "\n");
            System.out.print("Employee Salary: " + "\t" + employee.get(entry.getKey()).get(1) + "\n\n");
        }
    }

    public static void SaveFile() throws IOException {
        Path file = Paths.get("MyEmployeeList.txt");
        OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, APPEND, CREATE));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        String data;

        try{
            for(Map.Entry<String, List<Double>> entry: employee.entrySet())
            {
                data = entry.getKey() + " " + employee.get(entry.getKey()).get(0) + " " + employee.get(entry.getKey()).get(1) + "\n";
                writer.write(data);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        writer.close();
    }
}
