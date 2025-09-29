import java.util.ArrayList;
import java.util.Collections;

public class Homework5
{
    public static void main(String[] args)
    {
        Employee emp1 = new Employee(111, "Jimmy Dean", 5256.32, 0);
        Employee emp2 = new Employee(598, "Jen Johnson", 47370, 5);
        Employee emp3 = new Employee(920, "Jan Jones", 47834.25, 1);

        System.out.println(emp1.equals(emp3));

        ArrayList<Employee> list = new ArrayList<Employee>();

        list.add(emp1);
        list.add(emp2);
        list.add(emp3);

        //Before Sort()
        //for (Employee E : list)
        //System.out.println(E);

        //call the sort method here
        Collections.sort(list);
        //System.out.println("-----------------------------");

        //After Sort()
        for (Employee E : list)
            System.out.println(E);
    }
}

class Employee implements Comparable
{
    //Class Fields
    private String name;
    private int id;
    private int numberOfDependents;
    private double salary;


    //Getter and Setter Methods:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    //Class Constructors:


    //blank Constructor
    public Employee()
    {
        this.name = "N/A";
        this.id = 0;
        this.salary = 0.0;
        this.numberOfDependents = 0;
    }


    //pass in each parameter separately
    public Employee(int id, String name, double salary, int numberOfDependents)
    {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.numberOfDependents = numberOfDependents;
    }


    //Class Methods


    //toString Method -> [id,name,netSalary]
    @Override
    public String toString()
    {
        return "[" + this.id + "," + this.name + "," + String.format("%.2f",this.getNetSalary()) + "]";
    }


    //checks if 2 Employee Objects has the same netIncome
    @Override
    public boolean equals(Object O)
    {
        //Check if O is actually an Employee Object
        if(O.getClass() != Employee.class)
            return false;

        return (this.getNetSalary() == ((Employee) O).getNetSalary());
    }


    //calculates net salary of an Employee
    private double getNetSalary()
    {
        return salary * 0.91 + (this.numberOfDependents * 0.01 * salary);
    }


    //compares salary of 2 Employee Objects (-1 = less than, 0 = equal, 1 = greater than)
    @Override
    public int compareTo(Object O)
    {
        if(O.getClass() != Employee.class)
            return -1;

        double res = this.getNetSalary() - ((Employee) O).getNetSalary();

        if(res > 0)
            return 1;
        else if (res < 0)
            return -1;

        return 0;
    }
}