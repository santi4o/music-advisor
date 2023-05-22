package org.santiago.advisor.view;

import org.santiago.advisor.entities.Playlist;
import org.santiago.advisor.store.Page;
import org.santiago.advisor.store.PlaylistRepository;


public class PlaylistsPrintingFormat implements PrintingFormat {
    @Override
    public void printPage() {
        var playlists = PlaylistRepository.getInstance().getPage(Page.getInstance().getCurrentPage());
        for (var playlist : playlists) {
            System.out.println(((Playlist)playlist).name());
            System.out.println(((Playlist)playlist).url() + "\n");
        }
    }
}
