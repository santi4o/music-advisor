package org.santiago.advisor.entities;

import java.util.List;

public record Album (String name, List<String> artists, String url) { }
