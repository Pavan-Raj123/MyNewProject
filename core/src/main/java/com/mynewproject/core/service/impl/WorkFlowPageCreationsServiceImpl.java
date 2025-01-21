package com.mynewproject.core.service.impl;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.mynewproject.core.service.WorkFlowPageCreationService;


@Component(service = WorkFlowPageCreationService.class, immediate = true)
public class WorkFlowPageCreationsServiceImpl  implements WorkFlowPageCreationService {

     @Override
     public Page createPage(ResourceResolver resourceResolver, String pageName, String title) {
        String parentPath = "/content/mynewproject/us";
        String templatePath = "/conf/mynewproject/settings/wcm/templates/editabletemplate";

        Page page = null;
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            if (pageManager != null) {
                try {
                    page = pageManager.create(parentPath, pageName, templatePath, title);
                } catch (WCMException e) {
                    e.printStackTrace();
                }
            }

        return page;
    }
}