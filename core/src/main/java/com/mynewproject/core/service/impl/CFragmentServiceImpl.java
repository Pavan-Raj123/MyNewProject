package com.mynewproject.core.service.impl;

import com.mynewproject.core.service.CfService;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;

@Component(service = CfService.class, immediate = true)
public class CFragmentServiceImpl implements CfService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public void createCf() {
        logger.info("Running scheduled task");
        String cfModelPath = "/conf/mynewproject/settings/dam/cfm/models/user-cf-model";
        String cfFolder = "/content/dam/mynewproject";
        String cfName = "NewContentFragment";

        Map<String, Object> authInfo = new HashMap<>();
        authInfo.put(ResourceResolverFactory.SUBSERVICE, "assessmentuser");
        ResourceResolver resolver = null;
        

        try {
            resolver = resolverFactory.getServiceResourceResolver(authInfo);
        } catch (LoginException e) {
          
            e.printStackTrace();
        }
        Resource modelRes = resolver.getResource(cfModelPath);
        Resource fragmentFolderPath = resolver.getResource(cfFolder);

        FragmentTemplate template = modelRes.adaptTo(FragmentTemplate.class);

        String cfDescription = "Description for " + cfName;
        try {

            ContentFragment fragment = template.createFragment(fragmentFolderPath, cfName, cfDescription);

        } catch (ContentFragmentException e) {
           
            e.printStackTrace();
        }

        try {
            resolver.commit();
        } catch (PersistenceException e) {
           
            e.printStackTrace();
        }

    }
   

}
