package com.sdmx.controller.menu;

import com.sdmx.support.app.menu.Collection;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

public interface MenuCollection {

    public void setContext(ApplicationContext context);

    public void setRequest(HttpServletRequest request);

    public void build();

    public Collection getCollection();
}
