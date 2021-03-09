package dto;

import java.util.ArrayList;

public class ReaderDTO {
    private String title;
    private ArrayList<String> details = null;
    public ReaderDTO(String title, ArrayList<String> details){
        this.title = title;
        this.details = details;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public String getTitle() {
        return title;
    }
}
