package com.sdmx.support.app.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {

	private String label;

	private String url;

	private boolean enable;

	private Map<String, String> attr = new HashMap<>();

	private List<Item> children = new ArrayList<>();

	public Item(String label, String url) {
		this.label = label;
		this.url = url;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getAttr(String key) {
		return attr.get(key);
	}

	public Item setAttr(String name, String value) {
		attr.put(name, value);

		return this;
	}

	public boolean hasAttr(String name) {
		return attr.containsKey(name);
	}

	public List<Item> getChildren() {
		return children;
	}

	public void setChildren(List<Item> children) {
		this.children = children;
	}

	public Item addChildren(Item item) {
		children.add(item);

		return this;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}
}
