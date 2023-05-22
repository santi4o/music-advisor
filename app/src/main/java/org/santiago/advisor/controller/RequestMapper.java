package org.santiago.advisor.controller;

import org.santiago.advisor.entities.Category;
import org.santiago.advisor.parsers.*;
import org.santiago.advisor.store.CategoryRepository;
import org.santiago.advisor.store.Navigation;
import org.santiago.advisor.store.Page;
import org.santiago.advisor.view.AlbumsPrintingFormat;
import org.santiago.advisor.view.CategoriesPrintingFormat;
import org.santiago.advisor.view.PlaylistsPrintingFormat;
import org.santiago.advisor.view.Printer;
import com.google.gson.JsonObject;

public class RequestMapper implements Controller {
    private final RequestHandler requestHandler;
    private final Parser categoryParser;
    private final Page page;
    private final Printer printer;
    private final CategoryRepository categories;
    private final ParsingMethod parser;

    public RequestMapper() {
        this.requestHandler = RequestHandler.getInstance();
        this.page = Page.getInstance();
        this.categories = CategoryRepository.getInstance();
        this.categoryParser = new CategoryParser();
        this.printer = Printer.getInstance();
        parser = new ParsingMethod();
    }

    @Override
    public void mapRequest(String command) {
        if (command.equals("exit")) {
            if (Navigation.state.equals("main")) {
                printer.printMessage("---GOODBYE!---");
                Navigation.state = "out";
            } else if (Navigation.state.equals("pages")) {
                Navigation.state = "main";
            }
            return;
        }

        if (command.equals("new")) {
            requestHandler.setCurrentPath("/v1/browse/new-releases");
            parser.setParsingMethod(new AlbumParser());
            printer.setFormat(new AlbumsPrintingFormat());
        } else if (command.equals("featured")) {
            requestHandler.setCurrentPath("/v1/browse/featured-playlists");
            parser.setParsingMethod(new PlaylistParser());
            printer.setFormat(new PlaylistsPrintingFormat());
        } else if (command.equals("categories")) {
            requestHandler.setCurrentPath("/v1/browse/categories");
            parser.setParsingMethod(new CategoryParser());
            printer.setFormat(new CategoriesPrintingFormat());
        } else if (command.startsWith("playlists ")) {
            if (categories.getItems().size() == 0) {
                requestHandler.setCurrentPath("/v1/browse/categories");
                JsonObject jo = requestHandler.request();

                if (checkError(jo)) {
                    return;
                }

                categoryParser.parse(jo);
            }
            String categoryName = "";
            String categoryId = null;
            categoryName = command.replace("playlists ", "");

            for (Category category : categories.getItems()) {
                if (category.name().equals(categoryName)) {
                    categoryId = category.id();
                    break;
                }
            }

            if (categoryId == null) {
                printer.printMessage("Unknown category name");
                return;
            }

            requestHandler.setCurrentPath("/v1/browse/categories/" + categoryId + "/playlists");
            parser.setParsingMethod(new PlaylistParser());
            printer.setFormat(new PlaylistsPrintingFormat());
        }
        page.setCurrentPage(0);
        Navigation.state = "pages";
        JsonObject jo = requestHandler.request();
        if (checkError(jo)) {
            return;
        }
        parser.parse(jo);
        printer.printPage();
    }

    public static boolean checkError(JsonObject o) {
        JsonObject error = o.getAsJsonObject("error");
        if (error != null) {
            Printer.getInstance().printMessage(error.get("message").getAsString());
            return true;
        }
        return false;
    }
}
