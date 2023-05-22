package org.santiago.advisor.parsers;

import org.santiago.advisor.entities.Playlist;
import org.santiago.advisor.store.Page;
import org.santiago.advisor.store.PlaylistRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class PlaylistParser implements Parser {

    @Override
    public void parse(JsonObject object) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        for (JsonElement playList : object.getAsJsonObject("playlists").getAsJsonArray("items")) {
            playlists.add(new Playlist(
                    playList.getAsJsonObject().get("name").getAsString(),
                    playList.getAsJsonObject().getAsJsonObject("external_urls").get("spotify").getAsString()));
        }
        
        PlaylistRepository.getInstance().setItems(playlists);
        Page.getInstance().setCurrentTotalItems(playlists.size());
    }
}
