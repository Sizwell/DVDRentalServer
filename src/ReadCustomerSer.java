import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReadCustomerSer
{
    private ObjectInputStream oisCust;

    ArrayList<Customer> cust = new ArrayList<>();
    public void openFiles() throws IOException
    {
        oisCust = new ObjectInputStream(new FileInputStream("Customers.ser"));

    }

    public void closeFiles() throws IOException
    {
        oisCust.close();

    }

    public void readSerFiles() throws IOException, ClassNotFoundException
    {
        Customer tempCust;

        try
        {
            while (true)
            {
                tempCust = (Customer)oisCust.readObject();
                cust.add(tempCust);
            }

        }catch(EOFException eof)
        {
            System.out.println("End of file");
        }
        System.out.println("Serialized files read...");
        System.out.println(cust.toString());

    }

    public ArrayList<Customer> getCust()
    {
        return cust;
    }


}
