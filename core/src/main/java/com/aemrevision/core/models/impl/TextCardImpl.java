package com.aemrevision.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.aemrevision.core.models.TextCardModel;

@Model(adaptables = Resource.class, adapters = TextCardModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = TextCardImpl.RESOURCE_TYPE)
public class TextCardImpl implements TextCardModel {
	
	protected static final String RESOURCE_TYPE = "aem-revision/components/textcard";

	@ValueMapValue
	private int count;

	@ValueMapValue
	@Named(value = "text")
	private String name;

	private List<String> iterateName = new ArrayList<>();

	@PostConstruct
	private void init() {
		for (int i = 0; i < count; i++) {
			iterateName.add(name);
		}
	}
	
	@Override
	public List<String> getIterateName() {
		return iterateName;
	}

}
