package com.sdmx.controller.menu;

import com.sdmx.support.app.menu.Item;

public class AdminMenu extends MenuCollectionAbstract {

	public void build() {
		menuCollection
			.add(new Item("Dashboard", "/admin")
				.setAttr("icon", "fa fa-dashboard")
			)
			.add(new Item("User Management", "#")
				.setAttr("icon", "fa fa-users")
				.addChildren(new Item("Account", "/admin/account")
					.setAttr("icon", "fa fa-user"))
				.addChildren(new Item("Role", "/admin/role")
					.setAttr("icon", "fa fa-user-secret"))
				.addChildren(new Item("Permission", "/admin/permission")
					.setAttr("icon", "fa fa-lock"))
			)
			.add(new Item("Content Management", "#")
				.setAttr("icon", "fa fa-pencil-square-o")
				.addChildren(new Item("Category", "/admin/category")
					.setAttr("icon", "fa fa-hashtag"))
				.addChildren(new Item("News", "/admin/news")
					.setAttr("icon", "fa fa-newspaper-o"))
				.addChildren(new Item("Publication", "/admin/publication")
					.setAttr("icon", "fa fa-book"))
			)
			.add(new Item("Approval", "#")
				.setAttr("icon", "fa fa-check")
				.addChildren(new Item("News", "/admin/approval/news")
					.setAttr("icon", "fa fa-newspaper-o"))
				.addChildren(new Item("Publication", "/admin/approval/publication")
					.setAttr("icon", "fa fa-list-alt"))
			);

		menuCollection.match(request.getServletPath());
	}
}
