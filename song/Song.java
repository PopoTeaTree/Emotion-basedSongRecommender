package song;
/**
 *  song.Song.java
 *
 *  This class represents songs object which collects
 *  all song detail with title, lyrics, id and emotion score.
 *  It will calculate emotion scorce from words in lyric and
 *
 *  Created by
 *  Pinky Gautam ID: 60070503401,
 *  Thitiporn Sukpartcharoen ID: 60070503419
 *
 *  19 May 2020
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static utils.Utils.getWordCount;

public class Song {
    /**
     * song ID
     */
    private Integer id;

    /**
     * counter to increase song id
     */
    private static Integer counter = 0;

    /**
     * song title
     */
    private String title;

    /**
     * song lyrics
     */
    private ArrayList<String> lyrics;

    /**
     * emotion score based on lyrics
     */
    private HashMap<String, Double> emotionScore;

    /**
     * Constructor sets the id, title, lyrics and instantiate HashMap
     * of emotion score
     *
     * @param title  song title
     * @param lyrics song lyrics
     */
    public Song(String title, ArrayList<String> lyrics) {
        counter += 1;
        id = counter;
        this.title = title;
        this.lyrics = lyrics;
        emotionScore = new HashMap<>();
    }

    /**
     * Getter for song title
     *
     * @return song tile
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for song lyrics
     *
     * @return song lyrics
     */
    public ArrayList<String> getLyrics() {
        return lyrics;
    }

    /**
     * Getter for song ID
     *
     * @return tile song ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter for song's emotion score
     *
     * @return song's score or -1 if score not available
     */
    public Double getScore(String emotion) {
        /* emotion score exist */
        if (emotionScore.containsKey(emotion)) {
            return emotionScore.get(emotion);
        }
        return -1.0;
    }

    /**
     * Count lyrics score for emotion word
     * @param   emotionWord    word related to emotion
     * @return emotion score
     */

    private Double countWordScore(String emotionWord) {
        double wordCount = 0.0;
        double foundCount = 0.0;
        for(String line:lyrics)
        {
            /* find number of words in line using space */
            String[] words =  line.split("//s+");
            wordCount += words.length;
            foundCount += getWordCount(emotionWord, words);
        }
        if(wordCount > 0){
            return foundCount / wordCount;
        }
        return 0.0;
    }



    /**
     * Calculate emotion score from lyrics by counting number of words
     * which are related with emotion and divide by word count in lyrics.
     * @param emotion   emotion
     * @param words     words related to emotion
     */
    public void countEmotionScore(String emotion, ArrayList<String> words)
    {
        if(words.size() == 0) {
            emotionScore.put(emotion, 0.0);
        }
        double score = 0.0;
        /* Count emotion score */
        for(String word:words) {
            score += countWordScore(word);
        }
        emotionScore.put(emotion, score / words.size());
    }

}
