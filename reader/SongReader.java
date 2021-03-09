package reader;
/**
 * Class to read info about songs from a file
 * and create song.Song object.
 * <p>
 * Created by
 * Pinky Gautam ID: 60070503401,
 * Thitiporn Sukpartcharoen ID: 60070503419
 * <p>
 * 19 May 2020
 */

import song.Song;

import java.util.ArrayList;

public class SongReader extends TextFileReader {
    /** current song title which have been read from file */
    private static String currentSong = null;
    /** lyrics of current song that have been read */
    private static ArrayList<String> currentLyrics = null;

    private static int lyricsNotFoundCount = 0;
    private static int titleNotFoundCount = 0;
    private static final int maxNotFound = 10;

    /**
     * check if the line contains title and set current song title if found.
     * @param line current line
     * @return true if title found, false if the line does not have song title
     */
    private static boolean findAndSetTitle(String line) {
        String titlePattern = "Song :";
        int startIndex = line.indexOf(titlePattern);
        /* line contains title */
        if (!line.isEmpty() && startIndex != -1) {
            /* getting title from line */
            String title = line.substring(startIndex + titlePattern.length()).trim();
            if (!title.isEmpty()) {
                currentSong = title;
                titleNotFoundCount = 0;
                return true;
            }
        }
        titleNotFoundCount++;
        return false;
    }

    private static boolean parseAndSetLyrics(String line) {
        String lyricsPattern = "Lyrics :";
        int lyricsHeader = line.trim().compareTo(lyricsPattern);
        int songEnded = line.trim().compareTo("==END==");
        if (lyricsHeader != 0 && songEnded != 0) {
            currentLyrics.add(line.trim());
        }
        return songEnded == 0;
    }

    private static Song readLyrics() {
        String line;
        /* instantiate ArrayList when new song is read */
        currentLyrics = new ArrayList<>();
        Song newSong = null;
        while ((line = getNextLine()) != null && lyricsNotFoundCount < maxNotFound) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) {
                lyricsNotFoundCount++;
                continue;
            }
            boolean songEnded = SongReader.parseAndSetLyrics(trimmedLine);
            if (songEnded) {
                newSong = new Song(currentSong, currentLyrics);
                break;
            }
        }
        return newSong;
    }

    /*
     * read song from song file and create song object if
     * completed reading song.
     * @return  song object created or null if end of file
     * is reached
     */
    public static Song readSong() {
        Song newSong = null;
        String line = getNextLine();
        while (line != null && newSong == null) {
            /* eliminates leading and trailing spaces of a line */
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty()) {
                /* find and set song title */
                boolean foundTitle = findAndSetTitle(trimmedLine);
                if (foundTitle) {
                    newSong = readLyrics();
                }
                if (titleNotFoundCount >= maxNotFound) {
                    titleNotFoundCount = 0;
                    break;
                }
                if (lyricsNotFoundCount >= maxNotFound) {
                    lyricsNotFoundCount = 0;
                    break;
                }
            }
        }
        return newSong;
    }

}