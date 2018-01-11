package com.sdmx.controller.menu;

import com.sdmx.support.app.menu.Collection;
import org.springframework.context.ApplicationContext;
import javax.servlet.http.HttpServletRequest;

abstract class MenuCollectionAbstract implements MenuCollection {

	protected ApplicationContext context;

	protected HttpServletRequest request;

	protected Collection menuCollection;

	public MenuCollectionAbstract() {
		menuCollection = new Collection();
	}

	@Override
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void build() { }

	@Override
	public Collection getCollection() {
		build();
		return menuCollection;
	}
}
