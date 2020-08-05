package i5.las2peer.services.musicService;

import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

public class classes {

    class Song {

    public Song() {}
    
    private String title;

    public void settitle(String setValue) {
        this.title = setValue;
    }

    public String gettitle() {
        return this.title;
    }

    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        jo.put("title", this.title); 

        return jo;
    }

    public void fromJSON(String jsonString) throws ParseException {
        JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(jsonString);
        this.title = (String) jsonObject.get("title"); 

    }

}

    
}
