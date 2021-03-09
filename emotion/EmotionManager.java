package emotion;
/**
 *  emotion.EmotionManager.java
 *
 *  Manages emotions and provide information related
 *  to emotions.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import dto.ReaderDTO;
import reader.CustomReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EmotionManager
{
    /** instance of  emotion.EmotionManager for managing emotion */
    private static EmotionManager emotionManager= null;
    /** collection of all emotions */
    private ArrayList<Emotion> allEmotions;

    private final String emotionPattern = "Emotion :";
    private final String wordsPattern = "Words :";

    /**
     * Constructor class which creates ArrayList to store emotion information
     */
    private EmotionManager()
    {
        allEmotions = new ArrayList<>();
    }

    /**
     * Getter for emotion manager instance which is created
     * only once
     * @return emotion manager instance
     */
    public static EmotionManager getInstance()
    {
        if(emotionManager==null)
        {
            emotionManager = new EmotionManager();
        }
        return emotionManager;
    }

    /**
     * read emotion file
     * @param  fileName  emotion file name
     * @return true if successful, false if error.
     *         Error could involve failure while opening file
     */
     public boolean readEmotions(String fileName)
     {
         /* trying to open input file */
         if (!CustomReader.open(fileName))
         {
             System.out.println("Error opening emotion file " + fileName);
         }
         /* emotion read from file */
         ReaderDTO dataItem;
         /* loop to get emotion read from file */
         while ((dataItem = CustomReader.readData(emotionPattern, wordsPattern)) != null)
         {
             String emotion = dataItem.getTitle();
             ArrayList<String> words = dataItem.getDetails();
             if(emotion == null || words == null || words.size() == 0){
                 System.out.println("Invalid emotion data ==> skipping emotion");
                 continue;
             }
             allEmotions.add(new Emotion(emotion, words));
         }
         CustomReader.close();
         return allEmotions.size() > 0;
     }

    /**
     * Getter for emotions stored
     * @return all emotions stored
     */
    public ArrayList<Emotion> getEmotions()
    {
        return allEmotions;
    }

    /**
     * add emotion if it does not exist in the system yet
     * @param  emotion  emotion to add
     * @return true if successful, false if emotion already
     * exist in the system.
     */
    public boolean addEmotion(Emotion emotion)
    {
        boolean bOk = false;
        boolean duplicate = false;
        for (Emotion anEmotion: allEmotions)
        {
            if(anEmotion.getEmotion().compareToIgnoreCase(emotion.getEmotion())==0)
            {
                anEmotion.setEmotion(emotion.getEmotion());
                anEmotion.setWords(emotion.getWords());
                bOk = true;
                duplicate = true;
                break;
            }
        }
        if(!duplicate)
        {
            allEmotions.add(emotion);
            bOk = true;
        }
        return bOk;
    }

    /**
     * Write a text file using file writer
     * @return true if successfully written, false if failed to write.
     */
    public boolean writeEmotions()
    {
        boolean succeed = false;
        try
        {
            /* use file writer to help writing */
            FileWriter writer = new FileWriter("data/emotions.txt");
            for (Emotion emotion: allEmotions)
            {
                ArrayList<String> words = emotion.getWords();
                /* writing emotion category */
                writer.write(emotion.getEmotion().toLowerCase()+" : [\n");
                /* writing words for the emotion */
                for(String word : words)
                {
                    writer.write(word.toLowerCase()+"\n");
                }
                writer.write("]\n");
            }
            /* finished writing emotion file */
            writer.close();
            succeed = true;
        }
        /* in case error occurred */
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return succeed;
    }


}
