import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReadMoviesSer
{

    private ObjectInputStream oisMovies;
    ArrayList<DVD> dvd = new ArrayList<>();

    public void openFiles() throws IOException
    {

        oisMovies = new ObjectInputStream(new FileInputStream("Movies.ser"));

    }

    public void closeFiles() throws IOException
    {

        oisMovies.close();

    }

    public void readSerFiles() throws IOException, ClassNotFoundException
    {
        DVD tempDVD;

        try
        {
            while (true)
            {

                tempDVD = (DVD)oisMovies.readObject();

                dvd.add(tempDVD);
            }
        }catch(EOFException eof)
        {
            System.out.println("End of movie file..");
        }
        System.out.println("Serialized movie files have been read...");
        System.out.println(dvd.toString());

    }
    public ArrayList<DVD> getDvd()
    {
        return dvd;
    }
}
