package org.santiago.advisor.view;

import org.santiago.advisor.entities.Category;
import org.santiago.advisor.store.CategoryRepository;
import org.santiago.advisor.store.Page;

public class CategoriesPrintingFormat implements PrintingFormat {
    @Override
    public void printPage() {
        var categories = CategoryRepository.getInstance().getPage(Page.getInstance().getCurrentPage());
        for (var category : categories) {
            System.out.println(((Category)category).name());
        }
    }
}
