package org.santiago.advisor.store;

import org.santiago.advisor.entities.Category;

public class CategoryRepository extends Repository<Category> {
    private static final CategoryRepository INSTANCE = new CategoryRepository();

    private CategoryRepository() {}

    public static CategoryRepository getInstance() {
        return INSTANCE;
    }
}
