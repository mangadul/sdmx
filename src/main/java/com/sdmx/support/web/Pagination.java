package com.sdmx.support.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class Pagination {

	private int pageRecordSize = 20;

	private long totalRecord;

	private int currentPage = 1;

	private int prevPage = 0;

	private int nextPage = 2;

	private int maxPage;

	private Pageable pageable;

	public Pagination(long totalRecord, int pageRecordSize) {
		this.totalRecord = totalRecord;
		this.pageRecordSize = pageRecordSize;
		maxPage = (int) Math.ceil((double) totalRecord / (double) pageRecordSize);
	}

	public Pagination(long totalRecord, int pageRecordSize, Pageable pageable) {
		this.totalRecord = totalRecord;
		this.pageRecordSize = pageRecordSize;
		maxPage = (int) Math.ceil((double) totalRecord / (double) pageRecordSize);

		setPageable(pageable);
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
		setCurrentPage(pageable.getPageNumber() + 1);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		nextPage = currentPage < maxPage ? currentPage + 1 : 0;
		prevPage = currentPage > 1 ? currentPage - 1 : 0;
	}

	public Integer getPrevPage() {
		return prevPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public Integer getMaxPage() {
		return maxPage;
	}
}
