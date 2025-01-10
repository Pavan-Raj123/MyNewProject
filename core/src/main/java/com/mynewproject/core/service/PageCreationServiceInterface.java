package com.mynewproject.core.service;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.Page;

public interface PageCreationServiceInterface {
     Page createPage(ResourceResolver resourceResolver);

}
