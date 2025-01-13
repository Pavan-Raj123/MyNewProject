package com.mynewproject.core.service.impl;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.mynewproject.core.service.PageCreationServiceInterface;

@Component(service={PageCreationServiceInterface.class}, immediate = true)
public class PageCreationImplClass implements PageCreationServiceInterface{
    @Override
    public Page createPage(ResourceResolver resourceResolver) {
        String parentPath="/content/mynewproject/us";
        String pageName="Page1";
        String templatePath="/conf/mynewproject/settings/wcm/templates/editabletemplate";
        String title="Page";
        Page page=null;
        PageManager pageManager=resourceResolver.adaptTo(PageManager.class);
        if(pageManager != null){
         try{
         page = pageManager.create(parentPath,pageName,templatePath,title);
         }
         catch(WCMException wcmException){
             wcmException.printStackTrace();
         }
 
        }
        return page;
        
    }
}
