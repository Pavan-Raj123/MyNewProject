package com.mynewproject.core.service;
import org.apache.sling.api.resource.ResourceResolver;


import com.day.cq.wcm.api.Page;

public interface WorkFlowPageCreationService {
    
    Page createPage(ResourceResolver resourceResolver,String pageName, String title);
}
