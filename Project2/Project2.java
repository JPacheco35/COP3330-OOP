//John Pacheco
//COP 3330
//Project 2
//7.27.22

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Project2
{
    public static void main(String[] args) throws IOException
    {
        Menu.welcomeMessage();

        while(true)
        {
            Menu.printOptions();

            int test = Menu.getChoice();

            Menu.executeChoice(test);

            if(test == 7)
                break;
        }
        Menu.closingMessage();
    }
}


//Menu Class
class Menu
{
    //Class Objects:

    //Scanner used for reading User Input
    private static final Scanner input = new Scanner(System.in);

    //ArrayList of Person Objects used during runtime
    private static final ArrayList<Person> list = new ArrayList<Person>();


    //Class Methods:

    //displays opening message
    public static void welcomeMessage()
    {
        System.out.println("Welcome to the Personnel Management Program");
        System.out.println("----------------------------------");
    }


    //displays exiting message
    public static void closingMessage()
    {
        System.out.println("Goodbye!");
    }


    //displays all the menu options
    public static void printOptions()
    {
        System.out.println("\nChoose one of the options:");
        System.out.println("1- Enter the information a faculty");
        System.out.println("2- Enter the information of a student");
        System.out.println("3- Print tuition invoice for a student");
        System.out.println("4- Print faculty information");
        System.out.println("5- Enter the information of a staff member");
        System.out.println("6- Print the information of a staff member");
        System.out.println("7- Exit Program\n");
    }


    //asks user to pick an option (1-7) inclusive
    public static int getChoice()
    {
        char choice = '0';
        boolean isValid = false;
        int val = 0;

        //run until a valid option is selected
        while(!isValid)
        {
            System.out.println("Enter your selection: ");

            //read in input, pop off first char, checks if char is a digit
            String temp = Menu.input.nextLine();
            choice = temp.charAt(0);
            isValid = Character.isDigit(choice);
            val = Character.getNumericValue(choice);


            //Invalid Scenarios: > 1 character entered, not a digit, 8, 9, or 0 entered
            //if any invalid input is entered: loop back again
            if(temp.length() > 1 || !isValid || val > 7 || choice == '0')
            {
                System.out.println("Invalid Input, try again.");
                isValid = false;
            }
        }

        //return numeric value of choice
        return val;
    }


    //depending on choice, it runs that choice
    public static void executeChoice(int choice) throws IOException {
        switch(choice)
        {
            //Add a new Faculty Object
            case (1):
            {
                addFaculty();
                break;
            }

            //Add a new Student Object
            case (2):
            {
                addStudent();
                break;
            }

            //Print Invoice for an existing Student Object
            case (3):
            {
                studentInvoice();
                break;
            }

            //Print info for an existing Faculty Object
            case (4):
            {
                facultyInfo();
                break;
            }

            //Add a new Staff Object
            case (5):
            {
                addStaff();
                break;
            }

            //Print info for an existing Staff Object
            case (6):
            {
                staffInfo();
                break;
            }

            case (7):
            {
                outputFile();
                break;
            }

            default:
                break;
        }
    }

    //asks user for name, returns that String
    private static String readName()
    {
        //read in Name
        System.out.println("Name: ");
        String temp = input.nextLine().toLowerCase();

        //Formats name to this format: John Smith

        String[] words = temp.split(" ");
        String res = "";

        for(String S : words)
        {
            char capitalLetter = S.toUpperCase().charAt(0);
            S = S.substring(1);
            S = capitalLetter + S;
            res = res + S + " ";
        }

        return res;
        //return input.nextLine();
    }


    //asks user for ID, returns ID as String
    private static String readID()
    {
        //try-catch loop for ID
        //Proper Format: aa0000
        String id;
        do
        {
            try
            {
                //read in ID
                System.out.println("ID: ");
                id = input.nextLine().toLowerCase();

                //if invalid id format
                if(IdException.checkIDFormat(id, list))
                    throw new IdException();

                //if we reach here, id is good
                break;
            }
            catch (IdException IE)
            {

            }

        }while(true);

        return id;
    }


    //asks user for employee Department, returns it as a String
    private static String readDepartment()
    {
        //loop runs until user enters 1 of these 3 choices (Mathematics, Engineering, Sciences)
        String department;
        do
        {
            //read in input
            System.out.println("Department (Mathematics/Engineering/Sciences): ");
            department = input.nextLine();

            //format input (ie. Testing)
            char temp = department.toUpperCase().charAt(0);
            department = department.substring(1).toLowerCase();
            department = temp + department;

            //if we have an in valid entry
            if(!department.equals("Mathematics") && !department.equals("Engineering") && !department.equals("Sciences"))
                System.out.println("'" + department + "'" + " is invalid");
        }
        while(!department.equals("Mathematics") && !department.equals("Engineering") && !department.equals("Sciences"));
        return department;
    }


    //search list for an object of class C and of user specified ID, returns -1 if not found, index if found
    private static int findByID(Class c)
    {
        //ask for ID to look for
        System.out.println("Enter the Student's ID:");
        String id = input.nextLine();
        id = id.toLowerCase();

        //check each spot in list
        int index;
        for(index = 0; index < list.size(); index++)
        {
            //if we have a match (Id AND Class)
            if(list.get(index).getId().toLowerCase().equals(id) && c.isInstance(list.get(index)))
                return index;
        }

        //ID not found in list
        return -1;
    }


    //Option 1: user enters in info for a new Faculty object
    private static void addFaculty()
    {
        System.out.println("Enter Faculty Info:");
        System.out.println("----------------------------------");

        String name = readName();
        String id = readID();

        //read in Rank (Professor or Adjunct only)
        String rank;
        do
        {
            System.out.println("Rank (Professor/Adjunct): ");
            rank = input.nextLine();

            //format input
            char temp = rank.toUpperCase().charAt(0);
            rank = rank.substring(1).toLowerCase();
            rank = temp + rank;

            if(!rank.equals("Professor") && !rank.equals("Adjunct"))
            {
                System.out.println("'" + rank + "'" + " is invalid");
            }
        }
        while(!rank.equals("Professor") && !rank.equals("Adjunct"));


        //read in Department (Mathematics, Engineering, Sciences)
        String department = readDepartment();

        //list[listLength++] = new Faculty(name,id,department,rank);
        list.add(new Faculty(name,id,department,rank));

        System.out.println("----------------------------------");

        System.out.println("Faculty Added!");

    }


    //Option 2: user enters in info for a new Student object
    private static void addStudent()
    {
        System.out.println("Enter Student Info:");
        System.out.println("----------------------------------");

        //read in Name + ID
        String name = readName();
        String id = readID();
        String temp;


        double gpa;
        //try-catch loop for gpa
        do
        {
            try
            {
                System.out.println("GPA:");
                temp = input.nextLine();
                gpa = Double.parseDouble(temp);
                break;
            }
            catch(NumberFormatException NFE)
            {
                System.out.println(NFE.getMessage());
                System.out.println("Invalid Input,Try Again.");
            }
        }while (true);


        int creditHours;
        //try-catch loop for creditHours
        do
        {
            try
            {
                System.out.println("Credit Hours:");
                temp = input.nextLine();
                creditHours = Integer.parseInt(temp);
                break;
            }
            catch (NumberFormatException NFE)
            {
                System.out.println("Not a Valid Number, try again");
            }
        } while(true);

        list.add(new Student(name,id,gpa,creditHours));
        //list[listLength++] = new Student(name,id,gpa,creditHours);

        System.out.println("----------------------------------");

        System.out.println("Student Added!");

    }


    //Option 3: print tuition invoice of a student
    private static void studentInvoice()
    {
        //get index of student object
        int res = findByID(Student.class);

        //Student Does Not Exist
        if(res == -1)
            System.out.println("No Student was found with that ID.");

            //Student Exists
        else
            list.get(res).print();
    }


    //Option 4: prints info of a faculty member, if found by id search
    private static void facultyInfo()
    {
        //get index of Faculty
        int res = findByID(Faculty.class);

        //Faculty Does Not Exist
        if(res == -1)
            System.out.println("No Faculty was found with that ID.");

            //Faculty Exists
        else
            list.get(res).print();
    }


    //Option 5: User enter info for new Staff object, and it gets added to the list
    public static void addStaff()
    {
        System.out.println("Enter Staff Info:");
        System.out.println("----------------------------------");

        //read in Name + ID
        String name = readName();
        String id = readID();

        //read in Department
        String department = readDepartment();

        String status = null;
        char choice;
        boolean isValid = false;

        //run until valid option is selected
        while(!isValid)
        {
            System.out.println("Status (P = Part Time, F = Full Time): ");

            //read in line, pop off first char, check if it is a letter
            String temp = input.nextLine();
            temp = temp.toLowerCase(); //format input
            choice = temp.charAt(0);
            isValid = Character.isLetter(choice);

            //Invalid Scenarios: > 1 character entered, Not a letter
            if(temp.length() > 1 || !isValid)
            {
                System.out.println("Invalid Input, try again.");
                isValid = false;
                continue;
            }

            //Part Timer = 'p'
            if(choice == 'p')
            {
                status = "Part Time";
                break;
            }

            //Full Timer = 'f'
            if(choice == 'f')
            {
                status = "Full Time";
                break;
            }
        }

        //add item to array and update listLength
        //list[listLength++] = new Staff(name,id,department,status);
        list.add(new Staff(name,id,department,status));

        System.out.println("----------------------------------");
        System.out.println("Staff Added!");
    }


    //Option 6: prints info of a staff member, if found by id search
    private static void staffInfo()
    {
        //get index of Staff object
        int res = findByID(Staff.class);

        //Staff Does Not Exist
        if(res == -1)
            System.out.println("No Staff was found with that ID.");

            //Staff Exists
        else
            list.get(res).print();
    }


    //Option 7: Exit Program, but ask user for Output Data to a text file
    public static void outputFile() throws IOException {
        char choice;
        //read in Rank (Professor or Adjunct only)
        String rank;
        do
        {
            System.out.println("Would you like to create a Report File? (y/n)");
            rank = input.nextLine().toLowerCase();
            choice = rank.charAt(0);

            if(choice != 'y' && choice != 'n')
                System.out.println("Invalid Option, try again");
        }
        while(!(choice == 'y') && !(choice == 'n'));

        //if user wants Report File
        if(choice == 'y')
            writeReportFile();

    }


    //Writes Report File
    public static void writeReportFile() throws IOException
    {
        //Create the File if it does not exist already
        File reportFile = new File("report.txt");
        if(!reportFile.exists())
            reportFile.createNewFile();

        BufferedWriter Output = new BufferedWriter(new FileWriter(reportFile.getAbsolutePath()));

        //Collect the current Date and Time
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        //Log Date and Time of Writing Report
        Output.write("Report Generated on " + dateFormat.format(date) + " at " + timeFormat.format(date) + "\n\n");


        //Record all Staff Objects:
        Output.write("Faculty Members:\n");
        Output.write("-----------------------\n");

        int count = 1;
        for(Person P : list)
            if(P.getClass() == Faculty.class)
            {
                Output.write("\t" + count++ + ". " + P.getName() + "\n");
                Output.write("\t" + "ID: " + P.getId() + "\n");
                Output.write("\t" + ((Faculty) P).getRank() + "," + ((Faculty) P).getDepartment() + "\n");
                Output.write("\n");
            }


        //Record all Staff Objects
        count = 1;
        Output.write("Staff Members:\n");
        Output.write("-----------------------\n");

        for(Person P : list)
            if(P.getClass() == Staff.class)
            {
                Output.write("\t" + count++ + ". " + P.getName() + "\n");
                Output.write("\t" + "ID: " + P.getId() + "\n");
                Output.write("\t" + ((Staff) P).getDepartment() + "," + ((Staff) P).getStatus() + "\n");
                Output.write("\n");
            }


        //Record all Student Objects
        count = 1;
        Output.write("Students:\n");
        Output.write("-----------------------\n");

        for(Person P : list)
            if(P.getClass() == Student.class)
            {
                Output.write("\t" + count++ + ". " + P.getName() + "\n");
                Output.write("\t" + "ID: " + P.getId() + "\n");
                Output.write("\t" + "GPA: " + ((Student) P).getGpa() + "\n\t" + "Credit Hours: " + ((Student) P).getCreditHours() + "\n");
                Output.write("\n");
            }


        System.out.println("Report File report.txt Created.");
        Output.close();
    }
}


//Staffer Class
class Staff extends Employee
{
    //Class Fields
    private String status;


    //Getters and Setters

    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }


    //Constructors
    //blank
    public Staff()
    {
        super();
        this.status = "N/A";
    }

    //name + id
    public Staff(String name, String id)
    {
        super(name,id);
        this.status = "N/A";
    }

    //complete
    public Staff(String name, String id, String department, String status)
    {
        super(name,id,department);
        this.status = status;
    }


    //Class Methods

    //displays info about a staffer
    @Override
    public void print()
    {
        System.out.println("----------------------------------");
        System.out.println(this.getName() + "\t\t\t" + this.getId());
        System.out.println(this.getDepartment() + ", " + this.status);
        System.out.println("----------------------------------");
    }

}


//Student Class
class Student extends Person
{
    //Class Fields
    private double gpa;
    private int creditHours;


    //Constants used in calculating tuition
    private static final double CREDIT_HOUR_RATE = 236.45;
    private static final double ADMIN_FEES = 52.0;


    //Getters and Setters
    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }


    //Constructors

    //blank
    public Student()
    {
        super();
        this.gpa = 0.00;
        this.creditHours = 0;
    }

    //name only
    public Student(String name)
    {
        super(name);
        this.gpa = 0.00;
        this.creditHours = 0;
    }


    //complete constructor
    public Student(String name, String id, double gpa, int creditHours)
    {
        super(name,id);
        this.gpa = gpa;
        this.creditHours = creditHours;
    }


    //Class Methods:
    //returns tuition value for a student
    private double getTuition()
    {
        return (this.creditHours * CREDIT_HOUR_RATE) + ADMIN_FEES;
    }


    ///prints out the tuition invoice of a student
    @Override
    public void print()
    {
        System.out.println("Here is the Tuition Invoice for " + this.getName());
        System.out.println("----------------------------------");

        //display student info
        System.out.println("Name: " + this.getName());
        System.out.println("ID: " + this.getId());
        System.out.println("Credit Hours: " + this.creditHours + " ($236.45/credit hour)");

        //display prices
        System.out.printf("Tuition: $%.2f\n",(getTuition()-ADMIN_FEES));
        System.out.printf("Admin Fee: $%.2f\n",ADMIN_FEES);
        System.out.printf("Total: $%.2f\n",getTuition());

        //give discount if above gpa threshold
        if(this.gpa >= 3.85)
        {
            System.out.printf("Discount: $%.2f\n",getTuition()/4);
            System.out.printf("Total After Discount: $%.2f\n",getTuition()*0.75);
        }

        System.out.println("----------------------------------");
    }
}


//Abstract Employee Class
abstract class Employee extends Person
{
    //Class Fields
    private String department;


    //Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    //Class Constructors
    public Employee() {
        super();
        this.department = "N/A";
    }

    public Employee(String name, String id) {
        super(name, id);
        this.department = "N/A";
    }

    public Employee(String name, String id, String department) {
        super(name, id);
        this.department = department;
    }


    //Class Methods
    @Override
    public void print(){};
}


//Faculty Class
class Faculty extends Employee
{
    //Class Fields
    private String rank;


    //Getters and Setters
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }


    //Constructors

    //blank
    public Faculty()
    {
        super();
        this.rank = "N/A";
    }

    //name + id
    public Faculty(String name, String id)
    {
        super(name,id);
        this.rank = "N/A";
    }

    //complete constructor
    public Faculty(String name, String id, String department, String rank)
    {
        super(name,id,department);
        this.rank = rank;
    }

    //Class Methods

    //prints out information of a Faculty Object
    @Override
    public void print()
    {
        System.out.println("----------------------------------");
        System.out.println(this.getName() + "\t\t\t" + this.getId());
        System.out.println(this.getDepartment() + ", " + this.rank);
        System.out.println("----------------------------------");
    }

    @Override
    public String toString()
    {
        return "Hello";
    }
}


//ID Exception Class
class IdException extends Exception
{
    public static boolean checkIDFormat(String id, ArrayList<Person> list)
    {
        //improper length
        if(id.length() != 6)
        {
            System.out.println("Improper Length, Must be in format aa0000");
            return true;
        }

        //check if index 0 and 1 are letters
        for (int i = 0; i < 2; i++)
            if(!Character.isLetter(id.charAt(i)))
            {
                System.out.println("Improper Format, Must be in format aa0000");
                return true;
            }

        //checks if indexes 2-5 are numbers
        for (int i = 2; i < 6; i++)
            if(!Character.isDigit(id.charAt(i)))
            {
                System.out.println("Improper Length, Must be in format aa0000");
                return true;
            }

        //check the existing list of People to see if we have a duplicate
        for (Person P: list)
            if(P.getId().equals(id))
            {
                System.out.println("ID was already found, Try another ID");
                return true;
            }

        //is a valid ID Format
        return false;
    }
}


//Abstract Person Class
abstract class Person
{
    //Class Fields
    private String name;
    private String id;


    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    //Constructors

    //fully blank
    public Person()
    {
        this.name = "N/A";
        this.id= "aa0000";
    }

    //only a name
    public Person(String name)
    {
        this.name = name;
        this.id = "aa0000";
    }

    //both name and ID
    public Person(String name, String id)
    {
        this.name = name;
        this.id = id;
    }


    //Class Methods

    //method will be overwritten in child classes
    public abstract void print();


    //returns object's Name and ID
    public String toString()
    {
        return "Name: " + this.name + "\n" + "ID: " + this.id;
    }
}