package com.sdmx.controller.menu;

import com.sdmx.support.app.menu.Item;

public class UserMenu extends MenuCollectionAbstract {

	public void build() {
		menuCollection
			.add(new Item("Dashboard", "/admin")
				.setAttr("icon", "fa fa-dashboard")
			)
			.add(new Item("Report & Publication", "#")
				.setAttr("icon", "fa fa-newspaper-o")
			);

		menuCollection.match(request.getServletPath());
	}
}
