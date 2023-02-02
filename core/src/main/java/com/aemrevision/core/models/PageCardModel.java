package com.aemrevision.core.models;

import java.util.List;

import com.aemrevision.core.bean.PageItem;

public interface PageCardModel {
	
	List<PageItem> getPageDetail();
	List<PageItem> getChildPageDetail();
 
}
