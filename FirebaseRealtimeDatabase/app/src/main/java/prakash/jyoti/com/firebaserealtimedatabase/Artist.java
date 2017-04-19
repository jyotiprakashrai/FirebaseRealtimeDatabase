package prakash.jyoti.com.firebaserealtimedatabase;

/**
 * Created by jyotiprakashrai on 20/4/17.
 */

public class Artist {

    public String artistId;
    public String artistName;
    public String artistGenre;

    public Artist(){

    }

    public Artist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}
