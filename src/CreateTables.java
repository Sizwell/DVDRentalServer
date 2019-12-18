import java.io.*;
import java.sql.*;

public class CreateTables
{
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ObjectInputStream ois;

    ReadCustomerSer customerSer = new ReadCustomerSer();
    ReadMoviesSer moviesSer = new ReadMoviesSer();
    ReadRentalSer rentalSer = new ReadRentalSer();


    public CreateTables()
    {
        connectToDatabase();
        createAllTables();

        try
        {
            fillCustomerTable();
            fillMoviesTable();
            fillRentalsTable();

        }catch (IOException io)
        {
            System.out.println(io.getMessage());
        }catch (ClassNotFoundException cnf)
        {
            System.out.println(cnf.getMessage());
        }
    }

    private void connectToDatabase()
    {
        try
        {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\L-ZeT\\IdeaProjects\\DVDRentalServer\\Database.accdb");
            stmt = con.createStatement();
            System.out.println("Database created...");

        }catch (SQLException sql)
        {
            System.out.println(sql.getMessage());
        }
    }

    public void createAllTables()
    {
        try
        {
            stmt.executeUpdate("CREATE TABLE Customers (CustNumber INT NOT NULL, Name VARCHAR(30) NOT NULL, Surname VARCHAR(30) NOT NULL, PhoneNumber VARCHAR(30) NOT NULL, Credit DOUBLE, CanRent Boolean NOT NULL, PRIMARY KEY (CustNumber))");
            stmt.executeUpdate("CREATE TABLE Movies (DvdNumber INT NOT NULL, Title VARCHAR(50) NOT NULL, Category VARCHAR NOT NULL, RentAvailability Boolean NOT NULL, NewRelease Boolean NOT NULL, PRIMARY KEY (DVDNumber))");
            stmt.executeUpdate("CREATE TABLE Rentals (RentalNumber INT NOT NULL, RentDate VARCHAR(30) NOT NULL, CustNumber INT NOT NULL, DvdNumber INT NOT NULL, PRIMARY KEY (RentalNumber), FOREIGN KEY (CustNumber) REFERENCES Customers(CustNumber), FOREIGN KEY (DvdNumber) REFERENCES Movies(DvdNumber))");
            System.out.println("Tables successfully Created...");
        }
        catch(SQLException sql)
        {
            System.out.println(sql);
        }
    }




    private void fillCustomerTable() throws IOException, ClassNotFoundException
    {
        int count =0 ;
        try
        {

            ois = new ObjectInputStream(new FileInputStream("Customers.ser"));
            Customer cust;
            while(true)
            {
                cust = (Customer) ois.readObject();
                pstmt = con.prepareStatement("INSERT INTO Customers(CustNumber, Name, Surname, PhoneNumber, Credit, CanRent) VALUES (?,?,?,?,?,?)");
                pstmt.setInt(1,cust.getCustNumber());
                pstmt.setString(2,cust.getName());
                pstmt.setString(3,cust.getSurname());
                pstmt.setString(4,cust.getPhoneNum());
                pstmt.setDouble(5,cust.getCredit());
                pstmt.setBoolean(6,cust.canRent());
                pstmt.executeUpdate();
                count++;
            }
            //System.out.println("Customers table filled...");
            //System.out.println(customerSer.getCust().size() + " rows inserted.");
        }catch (EOFException eof)
        {
            System.out.println(eof.getMessage());
            return;
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle);
        }finally
        {
            System.out.println("No. of Customer: " + count);
        }
    }


    private void fillMoviesTable() throws IOException, ClassNotFoundException
    {
        int count = 0;
        try
        {

           ois = new ObjectInputStream(new FileInputStream("Movies.ser"));
           DVD dvd;
          while(true)
          {
              dvd = (DVD)ois.readObject();
              pstmt = con.prepareStatement("INSERT INTO Movies(DvdNumber, Title, Category, RentAvailability, NewRelease) VALUES (?,?,?,?,?)");
              pstmt.setInt(1,dvd.getDvdNumber());
              pstmt.setString(2,dvd.getTitle());
              pstmt.setString(3,dvd.getCategory());
              pstmt.setBoolean(4,dvd.isAvailable());
              pstmt.setBoolean(5,dvd.isNewRelease());
              pstmt.executeUpdate();
              count++;
          }

        }catch (EOFException eof)
        {
            System.out.println(eof.getMessage());
            return;
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle);
        }finally
        {
            System.out.println("No. of movies: "+count);
        }
    }



    private void fillRentalsTable() throws IOException, ClassNotFoundException
    {
        int count = 0;
        Rental rent;
        try
        {

            ois = new ObjectInputStream(new FileInputStream("Rental.ser"));


            while(true)
            {
                rent = (Rental) ois.readObject();
                pstmt = con.prepareStatement("INSERT INTO Rentals(RentalNumber, RentDate,  CustNumber, DvdNumber) VALUES (?,?,?,?)");
                pstmt.setInt(1,rent.getRentalNumber());
                pstmt.setString(2,rent.getDateRented());
                pstmt.setInt(3, rent.getCustNumber());
                pstmt.setInt(4, rent.getDvdNumber());
                pstmt.executeUpdate();
                count++;
            }
        }catch (EOFException eof)
        {
            System.out.println(eof.getMessage());
            return;
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle);
        }
        finally
        {
            System.out.println("No. of rentals: "+count);
        }
    }

    public static void main(String[] args)
    {
        new CreateTables();
    }
}
