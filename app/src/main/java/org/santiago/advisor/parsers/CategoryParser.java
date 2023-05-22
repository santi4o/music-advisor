package org.santiago.advisor.parsers;

import org.santiago.advisor.entities.Category;
import org.santiago.advisor.store.CategoryRepository;
import org.santiago.advisor.store.Page;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class CategoryParser implements Parser {

    @Override
    public void parse(JsonObject object) {
        ArrayList<Category> categories = new ArrayList<>();
        for (JsonElement category : object.getAsJsonObject("categories").getAsJsonArray("items")) {
            categories.add(new Category(
                    category.getAsJsonObject().get("id").getAsString(),
                    category.getAsJsonObject().get("name").getAsString())
            );
        }
        //super.getPaginationInfo(categoriesObject);
        CategoryRepository.getInstance().setItems(categories);
        Page.getInstance().setCurrentTotalItems(categories.size());
    }
}
