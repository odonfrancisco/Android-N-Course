package c.odonfrancisco.hackernewsreader;

public class HNitem {
    int id;
    String title;
    String url;

    public HNitem (int id, String title, String url){
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
