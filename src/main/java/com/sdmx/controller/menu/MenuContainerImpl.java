package com.sdmx.controller.menu;

import com.sdmx.support.app.menu.Collection;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

public class MenuContainerImpl implements MenuContainer {

	private ApplicationContext context;

	private HttpServletRequest request;

	public MenuContainerImpl(ApplicationContext context, HttpServletRequest request) {
		this.context = context;
		this.request = request;
	}

	@Override
	public Collection get(Class menuClass) {
		try {
			MenuCollection menu = (MenuCollection) menuClass.newInstance();
			menu.setContext(context);
			menu.setRequest(request);

			return menu.getCollection();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to initialize menu.");
		}

		return null;
	}
}
