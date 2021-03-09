package reader; /**
 *   reader.TextFileReader.java
 *
 *   Reads a text file line by line
 *
 *   Created by Sally Goldin, 21 March 2012
 *
 *   Used for project of
 *   Pinky Gautam ID: 60070503401,
 *   Thitiporn Sukpartcharoen ID: 60070503419
 *
 *   19 May 2020
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class encapsulates the weird Java file IO to give students
 * a way to read text files line by line that looks more like C
 */
public class TextFileReader
{
    /** Reader object to access the file */
    private static BufferedReader reader = null;

    /**
    * Open a text file, if possible. It will be closed
    * when we open a new file, or get to the end of the old one.
    * @param filename   File to open
    * @return true if successfully opened, false if not found.
    */
    public static boolean open(String filename)
    {
        boolean bOk = true;
        try
        {
            if (reader != null)
            reader.close();
        }
        catch (IOException io)
        {
            reader = null;
        }
        try
        {
            reader = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException fnf)
        {
            bOk = false;
            reader = null;
        }
        return bOk;
    }

    /**
    * Try to read a line from the open file.
    * @return Line as a string, or null if an error occurred.
    */
    public static String getNextLine()
    {
        String lineRead = null;
        try
        {
            if (reader != null)  /* if reader is null, file is not open */
            {
                lineRead = reader.readLine();
                if (lineRead == null)  /* end of the file */
                {
                    reader.close();
                }
            } /* end if reader not null */
        }
        catch (IOException ioe)
        {
            lineRead = null;
        }
        return lineRead;
    }
    public static boolean isEmptyLine(String line)
    {
        boolean isEmpty = line.length()==0;
        if(isEmpty)
        {
            //System.out.println("Empty line ==> skipping");
        }
        return isEmpty;
    }

    /**
     * Explicitly close the reader to free resources
     */
    public static void close()
    {
        try
        {
            reader.close();
        }
        catch (IOException ioe)
        {
        }
    }
}
