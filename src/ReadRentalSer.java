import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReadRentalSer
{

    private ObjectInputStream oisRentals;

    ArrayList<Rental> rent = new ArrayList<>();

    public void openFiles() throws IOException
    {

        oisRentals = new ObjectInputStream(new FileInputStream("Rental.ser"));
    }

    public void closeFiles() throws IOException
    {

        oisRentals.close();
    }

    public void readSerFiles() throws IOException, ClassNotFoundException
    {
        Rental tempRental;
        try
        {
            while (true)
            {
                tempRental = (Rental)oisRentals.readObject();
                rent.add(tempRental);
            }

        }catch(EOFException eof)
        {
            System.out.println("End of rental file");
        }
        System.out.println("Serialized rental files read...");
        System.out.println(rent.toString());

    }

    public  ArrayList<Rental> getRent()
    {
        return rent;
    }

    public static void main(String[] args)
    {
        ReadRentalSer read = new ReadRentalSer();
        try
        {
            read.openFiles();
            read.readSerFiles();
            read.closeFiles();
        }catch (IOException io)
        {
            System.out.println("IO exception");
        }catch (ClassNotFoundException cnf)
        {
            System.out.println("Cnf exception");
        }


    }
}
