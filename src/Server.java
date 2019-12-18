import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;

public class Server
{
    private ServerSocket listener;
    private Socket clientSocket;
    private InetAddress ipAddress;

    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    String msg = " ";

    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;


    public Server()
    {
        try{
        startServer();
        listen();
        createStreams();
        connectToDatabase();
        processClient();
        }catch (IOException io)
        {

        }catch (ClassNotFoundException cnf)
        {

        }

    }

    private void connectToDatabase()
    {
        try
        {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database.accdb");
            stmt = con.createStatement();
            System.out.println("Database created..");

        }catch (SQLException sql)
        {
            System.out.println(sql.getMessage());
        }
    }

    private void startServer()
    {
        try
        {
            listener = new ServerSocket(5050, 1);
            System.out.println("Socket created");
        }
        catch (IOException ioe)
        {

        }
      //  connection();
    }

    public void listen()
    {
        try
        {
            System.out.println("waiting for client");
            clientSocket = listener.accept();
            System.out.println("Accepted");
        }
        catch (IOException ioe)
        {

        }
    }


    public void createStreams()throws IOException
    {

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();

            in = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Client is online");

    }

    public void processClient() throws IOException, ClassNotFoundException
    {
        try
        {
            String client = "";
            do
            {
                 client = (String)in.readObject();
                 System.out.println("CLIENT SAYS>>>"+client);
                 ///retrieveCustomer
                if(client.equals("RetrieveDvdData>>>"))
                {
                    ArrayList<DVD> dvd  = new ArrayList<>();

                    rs = stmt.executeQuery("SELECT * FROM Movies");
                    System.out.println("get from movie table");
                    while (rs.next())
                    {
                        System.out.println("12345");
                        dvd.add(new DVD(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBoolean(4),rs.getBoolean(5)));

                    }
                    System.out.println("DVD read.."+ dvd);
                    out.writeObject(dvd);
                    out.flush();
                }


                if(client.equals("RetrieveCustomersData>>>"))
                 {
                   ArrayList<Customer> customer = new ArrayList<>();

                   rs = stmt.executeQuery("SELECT *FROM Customers");

                   while(rs.next())
                   {
                       System.out.println("reading cust");
                       customer.add(new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getBoolean(6)));
                   }
                   out.writeObject(customer);
                   out.flush();
                 }
                //retrieveDVD

                if (client.equals("RetrieveRentalData>>>"))
                {
                    ArrayList<Rental> rent = new ArrayList<>();

                    rs = stmt.executeQuery("SELECT * FROM Rentals");

                    while (rs.next())
                    {
                        rent.add(new Rental(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4)));
                    }
                    System.out.println(rent);
                    out.writeObject(rent);
                    out.flush();
                }

             }while (!client.equals("Terminate"));
        }catch (SQLException sql)
        {
            System.out.println(sql.getMessage());
        }
    }

    public static void main(String[] args)
    {
        new Server();
    }
}
