import java.io.IOException;

public class RunReadFiles
{
    public static void main(String[] args)
    {
        ReadCustomerSer custRead = new ReadCustomerSer();
        ReadMoviesSer movieRead = new ReadMoviesSer();
        ReadRentalSer rentRead = new ReadRentalSer();
        try
        {
            custRead.openFiles();
            movieRead.openFiles();
            rentRead.openFiles();
            custRead.readSerFiles();
            movieRead.readSerFiles();
            rentRead.readSerFiles();


            custRead.closeFiles();
            movieRead.closeFiles();
            movieRead.closeFiles();
        }catch (IOException io)
        {
            System.out.println(io.getMessage());
        }catch (ClassNotFoundException cnf)
        {
            System.out.println(cnf.getMessage());
        }

    }
}
