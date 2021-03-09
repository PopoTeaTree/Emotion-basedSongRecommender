package song; /**
 *  song.SongManager.java
 *
 *  This class represents organization to manage songs.
 *  It provides functionality to get songs in many ways.
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */
import dto.ReaderDTO;
import reader.CustomReader;

import java.util.ArrayList;

public class SongManager
{
    /** collection of song managed by manager */
    private SongCollection songs;

    /** instance of song.SongManager for managing songs */
    private static SongManager songManager= null;

    private final String titlePattern = "Song :";
    private final String lyricsPattern = "Lyrics :";


    /**
     * Constructor which instantiates song collection
     */
    private SongManager()
    {
        songs = new SongCollection();
    }

    /**
     * Getter for songManager instance which is created
     * only once.
     * @return  songManager instance
     */
    public static SongManager getInstance()
    {
        if(SongManager.songManager == null)
        {
            songManager = new SongManager();
        }
        return SongManager.songManager;
    }

    /**
     * Getter for all songs
     * @return all songs managed by the manager
     */
    public ArrayList<Song> getAllSongs()
    {
        return songs.getAllSongs();
    }

    /**
     * Getter for a song by song id
     * @param id id of song
     * @return song with specified id or null
     * if song not found
     */
    public Song getASong(Integer id)
    {
        return songs.getSongByID(id);
    }

    /**
     * Getter for songs from keyword
     * @param keyword keyword to find for
     * @return song(s) which title contains keyword
     */
    public ArrayList<Song> getSongs(String keyword)
    {
        return songs.getSongs(keyword);
    }

    /**
     * Open a song file and calling reader to read
     * and add songs read to song collection.
     * @param fileName  a song file name
     * @return true if successful, false if it cannot read songs.
     */
    public boolean readSongs(String fileName)
    {
        boolean result = false;
        /* trying to open song file */
        if (!CustomReader.open(fileName))
        {
            System.out.println("Error opening song file "+fileName);
            System.exit(1);
        }
        ReaderDTO dataItem = null;
        /* reading songs from song file */
        while ((dataItem = CustomReader.readData(titlePattern, lyricsPattern)) != null)
        {
            String songTitle = dataItem.getTitle();
            ArrayList<String> lyrics = dataItem.getDetails();
            if(songTitle == null || lyrics == null || lyrics.size() == 0){
                System.out.println("Invalid song data ==> skipping song");
                continue;
            }
            songs.addSong(new Song(songTitle,lyrics));
        }
        CustomReader.close();
        return songs.getAllSongs().size() > 0;
    }


}