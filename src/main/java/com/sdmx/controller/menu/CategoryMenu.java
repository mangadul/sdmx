package com.sdmx.controller.menu;

import com.sdmx.data.Category;
import com.sdmx.data.repository.CategoryRepository;
import com.sdmx.support.app.menu.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryMenu extends MenuCollectionAbstract {

	public void build() {
		CategoryRepository repo = (CategoryRepository) context.getBean("categoryRepository");
		List<Category> categories =  repo.findAll();

		Map<Long, List<Category>> childrenColl = new HashMap<>();

		for (Category item : categories) {
			// make children collections
			Long parentId = item.hasParent() ? item.getParent().getId() : null;

			if (!childrenColl.containsKey(parentId)) {
				childrenColl.put(parentId, new ArrayList<>());
			}

			childrenColl.get(parentId).add(item);
		}

		Item generalMenuItem = new Item("General", "?cat=general");
		menuCollection.add(generalMenuItem);

		makeTree(childrenColl, null, null);
		matchMenuItem("general", generalMenuItem);
//		menuCollection.match(request.getServletPath());
	}

	private void makeTree(Map<Long, List<Category>> collections, Long parentId, Item parentItem) {
		if (!collections.containsKey(parentId)) {
			return;
		}

		for (Category item : collections.get(parentId)) {
			Item menuItem = new Item(item.getName(), "?cat=" + item.getId());

//			// Data Flow
//			if (item.hasDataFlow()) {
//				for (DataFlow df : item.getDataFlow()) {
//					menuItem.addChildren(new Item(df.getName(), "/stats/"+ df.getId()));
//				}
//			}

			// set current menu
			matchMenuItem(item.getId().toString(), menuItem);

			if (parentItem == null) {
				menuCollection.add(menuItem);
			}
			else {
				parentItem.addChildren(menuItem);
			}

			if (collections.containsKey(item.getId())) {
				makeTree(collections, item.getId(), menuItem);
			}
		}
	}

	private void matchMenuItem(String key, Item menuItem) {
		String cat_id = request.getParameter("cat");

		if (cat_id != null && cat_id.equals(key)) {
			menuCollection.setCurrent(menuItem);
		}
	}
}
