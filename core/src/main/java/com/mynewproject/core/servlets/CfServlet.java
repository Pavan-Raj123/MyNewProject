package com.mynewproject.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mynewproject.core.service.CfService;
@Component(service=Servlet.class,
property = {
            ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/wvc/topics",
            ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
          
        }
)
public class CfServlet extends SlingAllMethodsServlet {
    @Reference
    private CfService CFragmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CfServlet.class);

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        try {
            
            CFragmentService.createCf();
            response.getWriter().write("Fragment created successfully.");
        } catch (Exception e) {
            LOGGER.error("Error triggering fragment creation.", e);
            response.getWriter().write("Error while creating the fragment: " + e.getMessage());
        }

    }
}
