package org.santiago.advisor.parsers;

import com.google.gson.JsonObject;

public interface Parser {
    void parse(JsonObject object);
}
