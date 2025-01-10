package com.mynewproject.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.wcm.api.Page;
import com.mynewproject.core.service.PageCreationServiceInterface;

@Component(service = Servlet.class,property = {
    "sling.servlet.methods=GET",
    "sling.servlet.paths=/bin/createpage"
})
public class PageCreationByServletPathBased extends SlingAllMethodsServlet {
    @Reference
    PageCreationServiceInterface pageCreationServiceInterface;

    ResourceResolver resourceResolver;

    @Override
    protected void doGet(final SlingHttpServletRequest req , final SlingHttpServletResponse resp)
    throws ServletException,IOException{
        try{
            resourceResolver =req.getResourceResolver();
            Page page=pageCreationServiceInterface.createPage(resourceResolver);
            if(page != null){
                resp.getWriter().write("Page is created Successfully :"+ page.getPath());
            }
            else{
                resp.getWriter().write("Page is Failed to Create ");
            }
        }catch(Exception e){
            resp.getWriter().write("An error Occured :");
        }
    }

}
