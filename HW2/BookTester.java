//John Pacheco
//COP 3330
//Homework #2
//5/28/22


import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;


public class BookTester
{
    public static void main(String[] args)
    {
        //opening message
        System.out.println("Welcome to the Book Program!");

        //holds user choice for the do-while loop
        String choice;

        //linked lists storing each of the book types
        LinkedList<BookstoreBook> BSBlist = new LinkedList<BookstoreBook>();
        LinkedList<LibraryBook> LBlist = new LinkedList<LibraryBook>();

        //create a new Scanner
        Scanner input = new Scanner(System.in);

        //run program loop until user says "no"
        do
        {
            //read in user choice for new book
            System.out.print("Would you like to create a new book object? (yes/no) ");
            choice = input.next();
            choice = choice.toLowerCase();

            //no option: break out of loop
            if(choice.equals("no"))
                break;

            //invalid option: try again
            if(!choice.equals("yes"))
            {
                System.out.println("Not a valid option, try again");
                choice = "yes";
                continue;
            }

            //ask user for generic fields:

            //ask for author, title, and isbn
            System.out.print("\nPlease Enter the Author, Title, and ISBN of the book separated by /: ");
            String temp = input.next();
            String [] fields = temp.split("/") ;


            String option;
            do
            {
                //ask user for type of book
                System.out.print("\nNow, tell me if it is a bookstore book or a library book (enter BB for bookstore book or LB for library book): ");

                //read in user option
                option = input.next();
                option = option.toLowerCase();

                //make a new BookstoreBook
                if(option.equals("bb"))
                {
                    //create new BookstoreBook object
                    BookstoreBook book = new BookstoreBook(fields);

                    //read in price
                    System.out.print("\nPlease enter the price of " + book.getTitle() + " by " + book.getAuthor() + " ");
                    book.setPrice(input.nextDouble());

                    //read in isOnSale
                    do
                    {
                        System.out.print("Is this book on sale? (yes/no) ");
                        String temp1 = input.next();
                        temp1 = temp1.toLowerCase();
                        if(temp1.equals("yes"))
                        {
                            book.setOnSale(true);
                            break;
                        }
                        else if (temp1.equals("no"))
                        {
                            book.setOnSale(false);
                            break;
                        }
                        System.out.println("Not a valid option, try again.");
                    }while(true);

                    //read in discount (if applicable)
                    if(book.isOnSale())
                    {
                        System.out.println("\nEnter the discount amount (%)");
                        book.setDiscount(input.nextInt());
                    }

                    //add new book to the list
                    BSBlist.add(book);

                    System.out.println("Here is your bookstore book information");
                    System.out.println(book + "\n");
                    break;
                }

                //make a new LibraryBook
                else if (option.equals("lb"))
                {
                    //create new LibraryBook object
                    LibraryBook book = new LibraryBook(fields);

                    LBlist.add(book);

                    System.out.println("Here is your library book information");
                    System.out.println(book + "\n");

                    break;
                }

                //invalid input
                else
                {
                    System.out.println("Not a valid option, try again");
                    option = "a";
                    continue;
                }

            }while(!option.equals("bb") || !option.equals("lb"));


        }while(choice.equals("yes"));

        //Display all books entered

        System.out.println("\nHere are all your books...");


        System.out.println("\nLibrary Books (" + LibraryBook.totalBooks + ")");
        for(LibraryBook lb : LBlist)
            System.out.println(lb);


        //print all BookStoreBooks
        System.out.println("\nBookstore Books (" + BookstoreBook.totalBooks + ")");
        for(BookstoreBook bsb : BSBlist)
            System.out.println(bsb);


        //good-bye message
        System.out.println("\nTake care now!");
        

        //close scanner at the end
        input.close();
    }
}


//BookstoreBook Class
class BookstoreBook
{
    //class fields
    private String author;
    private String title;
    private String isbn;
    private boolean isOnSale;
    private int discount;
    private double price;

    //keeps track of total # of BookstoreBooks classes instanced
    public static int totalBooks;


    //Getter() and Setter() methods
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
        this.author = "N/A";
        this.title = "N/A";
        this.isbn = "N/A";
        this.discount = 0;
        this.isOnSale = false;
        this.price = 0.0;
        totalBooks++;
    }

    //3 piece constructor (string array of (author, title, isbn) in that ordering)
    public BookstoreBook(String[] fields)
    {
        this.author = fields[0].toUpperCase();
        this.title = fields[1].toUpperCase();
        this.isbn = fields[2];
        this.discount = 0;
        this.isOnSale = false;
        this.price = 0.0;
        totalBooks++;
    }

    //6 piece constructor(full) (author + title + isbn + discount + onSale + price)
    public BookstoreBook(String author, String title, String isbn, int discount, boolean onSale, double price)
    {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.discount = discount;
        this.isOnSale = onSale;
        this.price = price;
        totalBooks++;
    }


    //Methods:

    //toString method for BookstoreBook
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("##.##");
        return "[" + this.isbn + "-" + this.title + " by "  + this.author + ", $" + this.price + " listed for $" + df.format(this.getDiscountPrice()) + "]";
    }

    private double getDiscountPrice()
    {
        return this.price - (this.price * (this.discount)/100);
    }
    
}


//LibraryBook Class
class LibraryBook
{
    //class fields
    private String author;
    private String title;
    private String isbn;
    private String callNumber;

    public static int totalBooks;


    //Getter and Setter Methods:

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

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }


    //Constructors:

    //completely blank constructor
    public LibraryBook()
    {
        this.author = "N/A";
        this.title = "N/A";
        this.isbn = "N/A";
        this.callNumber = "00.XXX.X";
        totalBooks++;
    }

    //3 piece constructor (string array of (author, title, isbn) in that ordering)
    public LibraryBook(String[] fields)
    {
        this.author = fields[0].toUpperCase();
        this.title = fields[1].toUpperCase();
        this.isbn = fields[2];
        this.callNumber = GenCallNum();
        totalBooks++;
    }

    //4 piece constructor(full) (author + title + isbn + callNumber)
    public LibraryBook(String author, String title, String isbn)
    {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.callNumber = GenCallNum();
        totalBooks++;
    }


    //Methods

    public String toString()
    {
        return "[" + this.isbn + "-" + this.title + " by "  + this.author + "-" + this.callNumber + "]";
    }

    private String GenCallNum()
    {
        Random rand = new Random();
        return (rand.nextInt(98) + 1) + "." + this.getFirstLetters() + "." + this.GetLastISBN();
    }

    //returns the 1st three chars of author's name as a string
    private String getFirstLetters()
    {
        return this.author.substring(0,3);
    }

    //returns last char of book's isbn
    private char GetLastISBN()
    {
        return isbn.charAt(this.isbn.length()-1);
    }

}
