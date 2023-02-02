package com.aemrevision.core.models.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.aemrevision.core.bean.PageItem;
import com.aemrevision.core.models.PageCardModel;
import com.day.cq.wcm.api.Page;

@Model(adaptables = Resource.class, adapters = PageCardModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = PageCardImpl.RESOURCE_TYPE)
public class PageCardImpl implements PageCardModel {

	protected static final String RESOURCE_TYPE = "aem-revision/components/pagecard";

	@ValueMapValue
	private String[] pages;

	@ValueMapValue
	private String childPath;

	@SlingObject
	private ResourceResolver resolver;

	private List<PageItem> pageDetail = new ArrayList<>();
	private List<PageItem> childPageDetail = new ArrayList<>();

	@PostConstruct
	private void init() {

		if (null == pages) {
			return;
		}

		for (String page : pages) {
			Resource resource = resolver.getResource(page);
			if (null != resource) {
				Resource childResource = resource.getChild("jcr:content");
				String title = childResource.getValueMap().get("jcr:title", String.class);
				String description = childResource.getValueMap().get("jcr:description", String.class);
				PageItem pageItem = new PageItem();
				pageItem.setTitle(title);
				if (null != description) {
					pageItem.setDescription(description.toUpperCase());
				}
				pageDetail.add(pageItem);
			}
		}
		
		addChildPages();
	}
	
	private void addChildPages() {
		if (null == childPath) {
			return;
		}

		Resource resource = resolver.getResource(childPath);
		if (null != resource) {
			Page page = resource.adaptTo(Page.class);
			Iterator<Page> childPageIterator = page.listChildren();
			while (childPageIterator.hasNext()) {
				Page childPage = childPageIterator.next();
				String childTitle = childPage.getTitle();
				String childDescription = childPage.getDescription();
				PageItem childPageItem = new PageItem();
				childPageItem.setTitle(childTitle);
				childPageItem.setDescription(childDescription);
				childPageDetail.add(childPageItem);
			}
		}
	}

	@Override
	public List<PageItem> getChildPageDetail() {
		return childPageDetail;
	}

	@Override
	public List<PageItem> getPageDetail() {
		return pageDetail;
	}

}
