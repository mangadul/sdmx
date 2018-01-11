package com.sdmx.support.app.menu;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    private List<Item> collection;

    private Item current;

    public Collection() {
        collection = new ArrayList<>();
    }

    public List<Item> getCollection() {
        return collection;
    }

    public Collection add(Item item) {
        collection.add(item);

        return this;
    }

    public Item getCurrent() {
        return current;
    }

    public boolean hasCurrent() {
        return current != null;
    }

    public void setCurrent(Item current) {
        this.current = current;
    }

    public Item match(String url) {
        current = null; // reset current active menu
        matchRecursively(url, getCollection());

        return current;
    }

    protected void matchRecursively(String url, List<Item> collection) {
        for (Item item : collection) {
            boolean url_match = url.toLowerCase().contains(item.getUrl().toLowerCase());

            if (url_match && (!hasCurrent() || item.getUrl().length() < current.getUrl().length())) {
                current = item;
            }

            if (item.hasChildren()) {
                matchRecursively(url, item.getChildren());
            }
        }
    }
}
