//John Pacheco
//COP 3330
//Homework #3
//7/6/22

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

public class Homework3
{
    public static void main(String[] args)
    {
        Menu.welcomeMessage();
        Menu.runChoice();
        Menu.goodbyeMessage();
    }
}


class Menu
{
    //Class Fields:
    private static int totalLibrary;
    private static int totalBookstore;
    private static int totalBooks;


    //Class Objects:

    //Used in reading User Input
    private static final Scanner input = new Scanner(System.in);

    //Used in storing all Books Objects
    private static final LinkedList<Book> list = new LinkedList<Book>();


    //Getters and Setters:
    public int getTotalBooks()
    {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks)
    {
        Menu.totalBooks = totalBooks;
    }

    public int getTotalLibrary() {
        return totalLibrary;
    }

    public void setTotalLibrary(int totalLibrary) {
        Menu.totalLibrary = totalLibrary;
    }

    public int getTotalBookstore() {
        return totalBookstore;
    }

    public void setTotalBookstore(int totalBookstore) {
        Menu.totalBookstore = totalBookstore;
    }


    //Class Methods:

    //Displays Welcome message
    public static void welcomeMessage()
    {
        System.out.println("Welcome to the Book Program!");
    }


    //Displays Goodbye message
    public static void goodbyeMessage()
    {
        System.out.println("\nTake care now!");
    }


    //Prints info of all Books in the list
    private static void printAllBooks()
    {
        System.out.println("\nHere are all your books...");


        //print all LibraryBooks
        System.out.println("\nLibrary Books (" + totalLibrary + ")");
        for (Book b : list)
        {
            if(b.getClass() == LibraryBook.class)
                System.out.println(b);

        }

        //print all BookStoreBooks
        System.out.println("\nBookstore Books (" + totalBookstore + ")");
        for (Book b : list)
        {
            if(b.getClass() == BookstoreBook.class)
                System.out.println(b);

        }
    }


    //adds libraryBook to the list
    private static void addLibBook()
    {
        LibraryBook book = LibraryBook.enterInfo();

        System.out.println("Here is your library book information");
        System.out.println(book + "\n");

        list.add(book);
        totalLibrary++;
    }


    //adds bookstoreBook to the list
    private static void addBSBook()
    {
        BookstoreBook book = BookstoreBook.enterInfo();

        System.out.println("Here is your bookstore book information");
        System.out.println(book + "\n");

        list.add(book);
        totalBookstore++;
    }


    //asks user what type of book they want to add (LB or BB) and returns choice as a String
    private static String getBookChoice()
    {
        //Enter in choice (bb or lb)
        String choice;
        do {
            System.out.println("Now, tell me if it is a bookstore book or a library book (enter BB for bookstore book or LB for library book): ");
            choice = input.nextLine();
            choice = choice.toLowerCase();

            if (!choice.equals("lb") && !choice.equals("bb"))
                System.out.println("Not a valid option, try again");


        }while(!choice.equals("lb") && !choice.equals("bb"));
        return choice;
    }


    //asks user if the want to add a Book (yes or no), and returns that option
    private static String getChoice()
    {
        //Enter in choice (bb or lb)
        String choice;
        do {
            System.out.print("Would you like to create a new book object? (yes/no) ");
            choice = input.nextLine();
            choice = choice.toLowerCase();

            if (!choice.equals("yes") && !choice.equals("no"))
                System.out.println("Not a valid option, try again");


        }while(!choice.equals("yes") && !choice.equals("no"));
        return choice;
    }


    //Add Books until the user enters "no", then displays all books and exits program
    public static void runChoice()
    {
        while(true)
        {
            String choice = getChoice();

            if(choice.equals("no"))
            {
                printAllBooks();
                break;
            }

            String bookChoice = getBookChoice();

            if(bookChoice.equals("lb"))
                addLibBook();

            else
                addBSBook();
        }
    }

}


abstract class Book
{
    //Class Fields:
    private String author;
    private String title;
    private String isbn;

    //Class Objects
    private static final Scanner input = new Scanner(System.in);

    //Getters and Setters:
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    //Class Constructors:

    //Blank
    public Book()
    {
        this.author = "N/A";
        this.title = "N/A";
        this.isbn = "N/A";
    }

    //Author/Title/ISBN as array of Strings
    public Book(String[] fields)
    {
        this.author = fields[0].toUpperCase();
        this.title = fields[1].toUpperCase();
        this.isbn = fields[2];
    }

    //Author/Title/ISBN as individual strings
    public Book(String author, String title, String isbn)
    {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
    }


    //Class Methods:

    //enter info for Book
    public static String[] enterBookInfo()
    {
        //ask user for generic fields:

        //ask for author, title, and isbn
        System.out.print("\nPlease Enter the Author, Title, and ISBN of the book separated by /: ");
        String temp = input.next();
        String [] fields = temp.split("/") ;
        return fields;
    }


    //toString Method
    @Override
    public String toString()
    {
        return "[" + this.isbn + "-" + this.title + " by "  + this.author;
    }
}


class BookstoreBook extends Book
{
    //Class Fields:
    private boolean isOnSale;
    private int discount;
    private double price;

    //Class Objects
    private static final Scanner input = new Scanner(System.in);


    //Getters and Setters:
    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    //Constructors:

    //completely blank constructor
    public BookstoreBook()
    {
        super();
        this.discount = 0;
        this.isOnSale = false;
        this.price = 0.0;
    }


    //3 piece constructor (string array of (author, title, isbn) in that ordering)
    public BookstoreBook(String[] fields)
    {
        super(fields);
        this.discount = 0;
        this.isOnSale = false;
        this.price = 0.0;
    }


    //6 piece constructor(full) (author + title + isbn + discount + onSale + price)
    public BookstoreBook(String author, String title, String isbn, int discount, boolean onSale, double price)
    {
        super(author, title, isbn);
        this.discount = discount;
        this.isOnSale = onSale;
        this.price = price;
    }


    //Methods:

    //toString Method
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("00.00");
        return super.toString() + ", $" + df.format(this.price) + " listed for $" + df.format(this.getDiscountPrice()) + "]";
    }


    //returns the discounted price of a book, if applicable
    private double getDiscountPrice()
    {
        return this.price - (this.price * (this.discount)/100);
    }

    public static BookstoreBook enterInfo()
    {
        //Enter in basic info
        String[] fields = Book.enterBookInfo();
        BookstoreBook bsb = new BookstoreBook(fields);

        //Enter in price
        System.out.print("\nPlease enter the price of " + bsb.getTitle() + " by " + bsb.getAuthor() + " ");
        bsb.setPrice(Double.parseDouble(input.nextLine()));


        //Enter in isOnSale (yes or no)
        String temp;
        do {
            System.out.println("Is this book on sale?");
            temp = input.nextLine();
            temp = temp.toLowerCase();

            if (temp.equals("yes"))
                bsb.setOnSale(true);

            else if (temp.equals("no"))
                bsb.setOnSale(false);

            else
                System.out.println("Invalid Input, try again.");

        }while(!temp.equals("yes") && !temp.equals("no"));


        //Enter in Discount, if applicable
        if(bsb.isOnSale)
        {
            System.out.println("Enter the discount amount:");
            bsb.discount = Integer.parseInt(input.nextLine());
        }

        return bsb;
    }
}


class LibraryBook extends Book
{
    //Class Fields:
    private String callNumber;

    //Getters and Setters:
    public String getCallNumber()
    {
        return callNumber;
    }


    public void setCallNumber(String callNumber)
    {
        this.callNumber = callNumber;
    }


    //Class Constructors:

    //Blank Constructor
    public LibraryBook()
    {
        super();
        this.callNumber = "00.XXX.X";
    }


    //Author + Name + ISBN as a String Array
    public LibraryBook(String[] fields)
    {
        super(fields);
        this.callNumber = GenCallNum();
    }


    //Author + Name + ISBN as individual Strings
    public LibraryBook(String author, String title, String isbn)
    {
        super(author,title,isbn);
        this.callNumber = GenCallNum();
    }


    //Class Methods:

    private String GenCallNum()
    {
        Random rand = new Random();
        return (rand.nextInt(98) + 1) + "." + this.getFirstLetters() + "." + this.GetLastISBN();
    }


    //returns the 1st three chars of author's name as a string
    private String getFirstLetters()
    {
        return this.getAuthor().substring(0,3);
    }


    //returns last char of book's isbn
    private char GetLastISBN()
    {
        return this.getIsbn().charAt(this.getIsbn().length()-1);
    }


    //toString Method
    public String toString()
    {
        return super.toString() + "-" + this.callNumber + "]";
    }

    public static LibraryBook enterInfo()
    {
        return new LibraryBook(Book.enterBookInfo());

    }

}