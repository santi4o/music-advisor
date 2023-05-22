package org.santiago.advisor.parsers;

import org.santiago.advisor.entities.Album;
import org.santiago.advisor.store.AlbumRepository;
import org.santiago.advisor.store.Page;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumParser implements Parser {

    @Override
    public void parse(JsonObject object) {
        ArrayList<Album> albums = new ArrayList<>();
        for (JsonElement album : object.getAsJsonObject("albums").getAsJsonArray("items")) {
            List<String> artists = new ArrayList<>();

            for (JsonElement artist : album.getAsJsonObject().getAsJsonArray("artists")) {
                artists.add(artist.getAsJsonObject().get("name").getAsString());
            }

            albums.add(new Album(
                    album.getAsJsonObject().get("name").getAsString(),
                    artists,
                    album.getAsJsonObject().getAsJsonObject("external_urls").get("spotify").getAsString()));
        }
        //super.getPaginationInfo(albumsObject);
        AlbumRepository.getInstance().setItems(albums);
        Page.getInstance().setCurrentTotalItems(albums.size());
    }
}
