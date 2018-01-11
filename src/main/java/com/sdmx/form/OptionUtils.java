package com.sdmx.form;

import com.sdmx.data.Category;
import com.sdmx.data.repository.CategoryRepository;
import com.sdmx.support.app.AppContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class OptionUtils {

	public static final Map<Long, String> getCategoriesOpt() {
		CategoryRepository repo = (CategoryRepository) AppContext.getContext().getBean("categoryRepository");
		List<Category> data = repo.findAllByOrderByName();
		Map<Long, String> options = new LinkedHashMap<>();

		for (Category item : data) {
			options.put(item.getId(), item.getName());
		}

		return options;
	}
}
