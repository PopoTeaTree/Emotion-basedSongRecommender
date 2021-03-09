package reader;

import dto.ReaderDTO;
import song.Song;

import java.util.ArrayList;

public class CustomReader extends TextFileReader {
    /** current song title which have been read from file */
    private static String currentTitle = null;
    /** lyrics of current song that have been read */
    private static ArrayList<String> currentDetails = null;

    private static int titleNotFoundCount = 0;
    private static int detailNotFoundCount = 0;
    private static final int maxNotFound = 10;
    private static  final String endPattern = "==END==";

    private static boolean findAndSetTitle(String line, String titlePattern) {
        String trimmedLine = line.trim();
        int startIndex = trimmedLine.indexOf(titlePattern);
        if (!trimmedLine.isEmpty() && startIndex != -1) {
            String title = line.substring(startIndex + titlePattern.length()).trim();
            if (!title.isEmpty()) {
                currentTitle = title;
                titleNotFoundCount = 0;
                return true;
            }
        }
        titleNotFoundCount++;
        return false;
    }

    private static boolean parseAndSetDetail(String line,  String detailPattern) {
        String trimmedLine = line.trim();
        if(trimmedLine.isEmpty()){
            detailNotFoundCount++;
            return false;
        }
        int detailHeader = trimmedLine.compareTo(detailPattern);
        int ended = trimmedLine.compareTo(endPattern);
        if (detailHeader != 0 && ended != 0) {
            currentDetails.add(trimmedLine);
            detailNotFoundCount = 0;
        }
        return ended == 0;
    }

    private static boolean readDetails(String detailPattern) {
        String line;
        boolean ended = false;
        /* instantiate ArrayList when new song is read */
        currentDetails = new ArrayList<>();
        while ((line = getNextLine()) != null && !maxInvalidDetailReached() && !ended) {
            ended = CustomReader.parseAndSetDetail(line, detailPattern);
        }
        return true;
    }

    private static boolean maxInvalidDetailReached() {
        return detailNotFoundCount >= maxNotFound;
    }
    private static boolean maxInvalidTitleReached() {
        return titleNotFoundCount >= maxNotFound;
    }
    /*
     * read song from song file and create song object if
     * completed reading song.
     * @return  song object created or null if end of file
     * is reached
     */
    public static ReaderDTO readData(String titlePattern, String detailPattern) {
        ReaderDTO newData = null;
        String line = getNextLine();
        while (line != null && newData == null) {
            boolean foundTitle = findAndSetTitle(line, titlePattern);
            if (maxInvalidTitleReached()) {
                newData = new ReaderDTO(currentTitle, currentDetails); // check null title, details
            }
            if (!foundTitle) {
                continue;
            }
            boolean detailsEnded = readDetails(detailPattern);
            if (detailsEnded) {
                newData = new ReaderDTO(currentTitle, currentDetails); // check no details (size == 0)
            }

        }
        return newData;
    }

}
