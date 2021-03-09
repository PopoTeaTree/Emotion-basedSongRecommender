package utils; /**
 *
 *  utils.Display.java
 *
 *  Help to display data like songs, emotions or
 *  even lyrics.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import emotion.Emotion;
import song.Song;

import java.util.ArrayList;

public class Display
{

    /**
     * Print songs in the song list provided
     * @param songs list of songs to print
     */
    public static void printSongs(ArrayList<Song> songs)
    {
        /* list does not have any songs */
        if(songs == null || songs.size()==0)
        {
            System.out.println("No songs available");
        }
        /* list contains song(s) */
        else
        {
            System.out.println(">> song.Song List <<");
            int i = 0;
            for (Song song:songs)
            {
                System.out.println((i+1)+" "+song.getTitle());
                i++;
            }
        }
    }

    /**
     * Print emotions in emotion list provided
     * @param allEmotions list of emotions to print
     */
    public static void printAllEmotions(ArrayList<Emotion> allEmotions)
    {
        if(allEmotions==null || allEmotions.size()==0)
        {
            System.out.println("No emotions available");
        }
        /* there exist some emotion(s) in the system */
        else
        {
            System.out.println(">> emotion.Emotion List <<");
            for (int counter = 0; counter < allEmotions.size(); counter++)
            {
                System.out.println((counter+1)+ " " + allEmotions.get(counter).getEmotion());
            }
        }
    }

    /**
     * Print lyrics of song provided
     * @param song song to print lyrics
     */
    public static void printLyrics(Song song)
    {
            if(song!=null)
            {
                ArrayList<String> currentLyrics = song.getLyrics();
                /* song has lyrics */
                if(currentLyrics!=null && currentLyrics.size()>0)
                {
                    System.out.println(">> Lyrics of " + song.getTitle() + " <<");
                    for (String currentPart : currentLyrics) {
                        System.out.println(currentPart);
                    }
                }
                else
                {
                    System.out.println("There are no lyrics for " + song.getTitle());
                }
            }
            else
            {
                System.out.println("Unable to print song lyrics");
            }

    }
}
