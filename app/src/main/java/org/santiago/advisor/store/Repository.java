package org.santiago.advisor.store;

import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T> {
    protected List<T> items;

    {
        items = new ArrayList<>();
    }

    private List<List<T>> pages;

    public void setItems(ArrayList<T> items) {
        this.items = items;
        genPages();
    }
    public List<T> getItems() {
        return items;
    }
    protected void genPages() {
        Page page = Page.getInstance();
        pages = new ArrayList<>();
        for (int i = 0; i < items.size(); i += page.getPageSize()) {
            pages.add(items.subList(i, Math.min(i + page.getPageSize(), items.size())));
        }
    }

    public List<?> getPage(int page) {
        try {
            return pages.get(page);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
