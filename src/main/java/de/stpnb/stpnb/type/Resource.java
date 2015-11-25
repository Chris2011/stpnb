package de.stpnb.stpnb.type;

import java.util.HashMap;
import java.util.Map;

public enum Resource {

    TEMPLATE("include", 2),
    JAVASCRIPT("javascript", 3),
    CSS("css", 3),
    THEMED_CSS("themedCSS", 3);

    private static final Map<String, Resource> lookup = new HashMap<>();

    static {
        for(Resource resource: Resource.values()) {
            lookup.put(resource.getType(), resource);
        }
    }
    
    private final String type;
    private final int offset;
    
    private Resource(String type, int offset) {
        this.type = type;
        this.offset = offset;
    }

    public String getType() {
        return type;
    }

    public static Resource fromString(String type) {
        return lookup.get(type);
    }

    public int getOffset() {
        return offset;
    }
}
