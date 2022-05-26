package com.java.model;

public class SearchAndSort {
	private String key;
	private int page;
	private int size;
	private String sortKey;
	private boolean oldOrNew;
	private long cateId;
	public SearchAndSort() {
		this.page = 0;
		this.size = 1;
		this.sortKey = "id";
		this.oldOrNew = true;
		this.cateId = 0;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public boolean isOldOrNew() {
		return oldOrNew;
	}
	public void setOldOrNew(boolean oldOrNew) {
		this.oldOrNew = oldOrNew;
	}
	public long getCateId() {
		return cateId;
	}
	public void setCateId(long cateId) {
		this.cateId = cateId;
	}
	
	
	
}
