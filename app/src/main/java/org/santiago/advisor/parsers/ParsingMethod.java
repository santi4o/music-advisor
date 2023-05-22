package org.santiago.advisor.parsers;

import com.google.gson.JsonObject;

public class ParsingMethod {
    private Parser parser;

    public void setParsingMethod(Parser parser) {
        this.parser = parser;
    }

    public void parse(JsonObject object) {
        parser.parse(object);
    }
}
