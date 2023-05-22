package org.santiago.advisor.view;

import org.santiago.advisor.entities.Album;
import org.santiago.advisor.store.AlbumRepository;
import org.santiago.advisor.store.Page;

public class AlbumsPrintingFormat implements PrintingFormat {
    @Override
    public void printPage() {
        var albums = AlbumRepository.getInstance().getPage(Page.getInstance().getCurrentPage());

        for (var album : albums) {
            System.out.println(((Album)album).name());
            System.out.println(((Album)album).artists());
            System.out.println(((Album)album).url() + "\n");
        }
    }
}
