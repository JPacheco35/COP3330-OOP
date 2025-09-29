import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Homework4
{
    public static void main(String[] args) throws IOException
    {
        //Opening Message
        System.out.println("Reading Data from lec.txt...");

        //Main Program Runtime
        CourseList.readFile();
        CourseList.runMenu();

        //Exiting and Writing Output File
        System.out.println("Writing Data to lecturesOnly.txt...");
        CourseList.outputToFile();

        //Closing Message
        System.out.println("File Written, Goodbye!");


    }

}



//CourseList Class
class CourseList
{
    //Class Objects
    private static File text;
    private static final Scanner Input = new Scanner(System.in);
    private static final LinkedList<Course> List = new LinkedList<Course>();


    //Class Methods

    //Reads "lec.txt" line by line and creates Course Objects for each Course/LabSection
    public static void readFile() throws IOException
    {
        //opening message
        System.out.println("Reading Input File...");

        File input = new File("lec.txt");
        BufferedReader file = new BufferedReader(new FileReader(input));

        //reads txt file line by line
        InPersonCourse labsCourse = null; //holds IPC object address only if it has Lab Sections
        for(String line; (line = file.readLine()) != null; )
        {
            //Read in line and split into course components
            String[] courseDetails = line.split(",");

            //InPerson Course = 7 parameters
            if(courseDetails.length == 7)
            {
                //add new Course to Linked List
                InPersonCourse IPC = new InPersonCourse(courseDetails);
                List.add(IPC);
                //System.out.println("New Course Added.");

                //If a Course has Labs: temp holds address to this course
                if(IPC.isLabs())
                    labsCourse = IPC;
            }

            //Online Course = 5 parameters
            else if(courseDetails.length == 5)
            {
                //add new Course to Linked List
                List.add(new Course(courseDetails));
                //System.out.println("New Online Course Added.");
            }

            //Otherwise: Lab Section
            else
            {
                //add new LabSection to labs field of InPersonCourse temp
                labsCourse.getLabs().add(new LabSection(courseDetails));
                //System.out.println("\tLab Section Added.");
            }
        }

        System.out.println("Enter the Classroom:");
        //read in the classroom

        //output crn of lab section or lecture found there

        System.out.println("Writing Data to noLabLectures.txt...");

        System.out.println("Goodbye!");
    }


    //returns number of Courses of Class type c in CourseList.List
    public static int getNumClass(Class c)
    {
        int res = 0;

        for(Course C : List)
            if(C.getClass() == c)
                res++;

        return res;
    }


    //Writes to text file, recording all courses without lab sections
    public static void outputToFile() throws IOException
    {
        File file = new File("lecturesOnly.txt");
        FileWriter output = new FileWriter(file);
        BufferedWriter BW = new BufferedWriter(output);

        //System.out.println("Writing Online Lectures to File...");

        //Write All Online Courses
        BW.write("Online Lectures:\n");
        for(Course C : List)
            if(C.getClass().equals(Course.class))
                BW.write(C.toString()+"\n");


        //System.out.println("Writing No Lab Lectures to File...");

        //Write All InPersonCourses without labs
        BW.write("\nLectures With No Labs:\n");
        for(Course C : List)
            if(C.getClass().equals(InPersonCourse.class))
                if (((InPersonCourse) C).isLabs())
                    BW.write(C.toString()+"\n");

        //System.out.println("File Successfully Written");
        BW.close();

    }

    private static void printMenuOptions()
    {
        System.out.println("Select An Option: ");
        System.out.println("---------------------");
        System.out.println("1.See Courses:");
        System.out.println("2.Lookup Course CRN:");
        System.out.println("3.Write Data To File / Exit Program:");
    }

    public static void runMenu()
    {
        int option = 0;
        while (option != 3)
        {
            printMenuOptions();

            option = getChoice();

            switch (option)
            {
                case 1:
                    runOption1();
                    break;

                case 2:
                    runOption2();

                default:
                    break;

            }

        }
    }

    //displays number of In Person Courses and Online Courses
    private static void runOption1()
    {
        System.out.println("----------------------------");

        int res = 0;

        //counts number of Online Only Courses
        for(Course C : List)
            if(C.getClass() == Course.class)
                res++;

        System.out.println("There are " + res + " Online Courses Offered");
        System.out.println("There are " + (List.size() - res) + " In Person/Mixed Courses Offered");

        System.out.println("----------------------------");
    }

    //Find CRN given a location;
    private static void runOption2()
    {
        System.out.println("----------------------------");
        System.out.println("Enter The Classroom: ");
        String temp = Input.nextLine();
        boolean notFound = true;

        //search entire list for a matching Location
        for(Course C : List)
        {
            //Only if IPC, since Online has not location
            if(C.getClass() == InPersonCourse.class)
            {
                //if location matches Course itself
                if(((InPersonCourse) C).getLocation().equals(temp))
                {
                    System.out.println("Lecture for " + C.getCourseCode() + " Found: ");
                    System.out.println("CRN: " + C.getCrn());
                    notFound = false;
                }

                //check if the IPC has Labs
                if (((InPersonCourse) C).isLabs())
                {
                    //Check each of the labs for a match
                    for(LabSection LS : ((InPersonCourse) C).getLabs())
                    {
                        if(LS.getLocation().equals(temp))
                        {
                            System.out.println("Lab Section for " + C.getCourseCode() + " Found: ");
                            System.out.println("CRN: " + LS.getCrn());
                            notFound = false;
                        }
                    }

                }
            }
        }

        //print only nothing was found at that location
        if(notFound)
            System.out.println("No Course or Lab Section was found at that location, try again.");

        System.out.println("----------------------------");
    }

    private static int getChoice()
    {
        //read in the user input
        boolean isValid = false;
        char choice;
        int value = 0;

        //run until a valid option is selected
        while(!isValid)
        {
            System.out.println("Enter Option: ");

            //read in input, pop off first char, checks if char is a digit
            String temp = Input.nextLine();
            choice = temp.charAt(0);
            isValid = Character.isDigit(choice);
            value = Character.getNumericValue(choice);


            //Invalid Scenarios: > 1 character entered, not a digit, 8, 9, or 0 entered
            //if any invalid input is entered: loop back again
            if(temp.length() > 1 || !isValid || value > 3 || choice == '0')
            {
                System.out.println("Invalid Input, try again.");
                isValid = false;
            }
        }
        return value;
    }



}


//Course Class
//Note: OBJECTS INSTANTIATED AS CLASS COURSE ARE CONSIDERED ONLINE ONLY CLASSES:
class Course
{
    //Class Fields
    private String crn;
    private String courseCode;
    private String courseName;
    private String courseLevel;
    private String modality;


    //Getters and Setters
    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }


    //Class Constructors
    //blank constructor
    public Course()
    {
        this.crn = "00000";
        this.courseCode = "XXX.0000";
        this.courseName = "N/A";
        this.courseLevel = "N/A";
        this.modality = "N/A";
    }

    //fill with String Array
    public Course(String[] fields)
    {
        this.crn = fields[0];
        this.courseCode = fields[1];
        this.courseName = fields[2];
        this.courseLevel = fields[3];
        this.modality = fields[4];
    }

    //Class Methods

    public String toString()
    {
        //Format: [CRN,Code,Name,Level,Mode,Location,Yes/No]
        return this.getCrn() + "," + this.getCourseCode() + "," + this.getCourseName() + "," +
                this.getCourseLevel() + "," + "Online";
    }
}


//InPersonCourse Class
class InPersonCourse extends Course
{
    //Class Fields
    private String location;
    private boolean isLabs;
    private ArrayList<LabSection> labs;


    //Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isLabs() {
        return isLabs;
    }

    public void setLabs(boolean labs) {
        isLabs = labs;
    }

    public ArrayList<LabSection> getLabs() {
        return labs;
    }

    public void setLabs(ArrayList<LabSection> labs) {
        this.labs = labs;
    }

    //Class Constructors
    //blank constructor
    public InPersonCourse()
    {
        super();
        this.location = "XXX.000";
        this.isLabs = false;
        this.labs = null;
    }

    //constructor with String Array of fields
    public InPersonCourse(String[] fields)
    {
        super(fields);
        this.location = fields[5];

        this.isLabs = fields[6].toLowerCase().equals("yes");

        this.labs = null;
        if(this.isLabs)
            this.labs = new ArrayList<LabSection>();

    }

    public String toString()
    {
        String temp = "No";
        if(this.isLabs())
            temp = "Yes";

        //Format: [CRN,Code,Name,Level,Mode,Location,Yes/No]
        return this.getCrn() + "," + this.getCourseCode() + "," + this.getCourseName() + "," +
                this.getCourseLevel() + "," + this.getModality() + "," + this.location + "," + temp;
    }



}

class LabSection
{
    //Class Fields
    private String crn;
    private String location;

    //Getters and Setters
    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //Constructors
    public LabSection(String[] fields)
    {
        this.crn = fields[0];
        this.location = fields[1];
    }


}