package com.mynewproject.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.wcm.api.Page;
import com.mynewproject.core.service.PageCreationServiceInterface;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
    resourceTypes = "mynewproject/components/profile",
    methods = HttpConstants.METHOD_GET
)
public class PageCreationByServletResourceBased extends SlingSafeMethodsServlet {
    @Reference
    PageCreationServiceInterface pageCreationServiceInterface;
    ResourceResolver resourceResolver;
     @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException{
        resourceResolver=req.getResourceResolver();
        
        try{
            Page page=pageCreationServiceInterface.createPage(resourceResolver);
        if(page != null){
            resp.getWriter().write("Page is created succussfully by Your Resouce :"+page.getPath());
        }
        else{
            resp.getWriter().write("Fialed to create page using your Resource :");
        }
    }catch(Exception e){
        resp.getWriter().write("An error occured please try again by giving proper Resource"+ e.getMessage());;
    }
   

    }


}
