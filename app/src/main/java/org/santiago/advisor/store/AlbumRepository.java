package org.santiago.advisor.store;

import org.santiago.advisor.entities.Album;

public class AlbumRepository extends Repository<Album> {
    private static final AlbumRepository INSTANCE = new AlbumRepository();

    private AlbumRepository() {}

    public static AlbumRepository getInstance() {
        return INSTANCE;
    }
}
