package org.santiago.advisor.store;

public class Page {
    private static final Page INSTANCE = new Page();
    private int pageSize;
    private int currentPage;
    private int currentTotalPages;

    private Page() { }
    
    public static Page getInstance() {
        return INSTANCE;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage >= 0) {
            this.currentPage = currentPage;
        }
    }

    public boolean decrementPage() {
        if (currentPage == 0) {
            return false;
        }
        currentPage -= 1;
        return true;
    }

    public boolean incrementPage() {
        if (currentPage == currentTotalPages - 1) {
            return false;
        }
        currentPage += 1;
        return true;
    }

    public int getCurrentTotalPages() {
        return currentTotalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentTotalItems(int currentTotalItems) {
        this.currentTotalPages = (int) Math.ceil((double) currentTotalItems / pageSize);
    }
}
