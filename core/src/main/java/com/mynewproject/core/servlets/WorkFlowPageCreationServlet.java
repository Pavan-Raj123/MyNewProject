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
import org.apache.sling.api.resource.ResourceResolverFactory;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.day.cq.workflow.WorkflowService;
import com.mynewproject.core.service.WorkFlowPageCreationService;


@Component(service = Servlet.class, property = {
    "sling.servlet.methods=GET",
    "sling.servlet.methods=POST",
    "sling.servlet.paths=/bin/workflow/createpage"  //this is the path to hit for creating the page
})
public class WorkFlowPageCreationServlet extends SlingAllMethodsServlet {

    @Reference
    private WorkFlowPageCreationService workFlowpageCreationService;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private WorkflowService workflowService; // WorkflowService reference

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try {
            response.getWriter().write("GET method is called. Thank you.");
        } catch (Exception e) {
            response.getWriter().write("Error creating pages: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
       
        ResourceResolver resourceResolver = null;
        
            resourceResolver = request.getResourceResolver();
        
        
        if (resourceResolver != null) {
            try {
                // Create pages using PageCreationService
                workFlowpageCreationService.createPage(resourceResolver, "page-1", "Page 1");
            
                response.getWriter().write("Pages created successfully.");



                // Trigger Workflow for created pages
                String playLoad="/content/mynewproject/us/page-1";
               

                try{
                   WorkflowSession workflowSession=resourceResolver.adaptTo(WorkflowSession.class);
                   WorkflowModel workflowMode=workflowSession.getModel("/var/workflow/models/trainingmodel");
                   WorkflowData workflowData=workflowSession.newWorkflowData("JCR_PATH", playLoad);
                 
                   workflowSession.startWorkflow(workflowMode, workflowData);
                   
                }
                catch(Exception e){
                    response.getWriter().write("unable to do that please");

                }
               

            } catch (Exception e) {
                response.getWriter().write("Error creating pages: " + e.getMessage());
            }
        } else {
            response.getWriter().write("Failed to obtain ResourceResolver.");
        }
    }

   
}

