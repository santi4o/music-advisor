package org.santiago.advisor.store;

import org.santiago.advisor.entities.Playlist;

public class PlaylistRepository extends Repository<Playlist> {
    private static final PlaylistRepository INSTANCE = new PlaylistRepository();
    private PlaylistRepository() {}

    public static PlaylistRepository getInstance() {
        return INSTANCE;
    }
}
