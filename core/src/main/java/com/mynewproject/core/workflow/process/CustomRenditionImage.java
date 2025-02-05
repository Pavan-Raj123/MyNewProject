package com.mynewproject.core.workflow.process;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Component(service = WorkflowProcess.class, property = { "process.label=Custom Rendition Process for an Image" })
public class CustomRenditionImage implements WorkflowProcess {

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
            throws WorkflowException {

        ResourceResolver resolver = null;
        Session session = null;
        try {
            // Get the service user session
            resolver = resolverFactory.getServiceResourceResolver(
                    Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "AssessmentUser"));
            session = resolver.adaptTo(Session.class);

            if (session == null) {
                throw new WorkflowException("Unable to obtain JCR session");
            }

            // Get the asset path from workflow metadata
            String assetPath = workItem.getWorkflowData().getPayload().toString();
            Resource assetResource = resolver.getResource(assetPath + "/jcr:content/renditions/original");
            if (assetResource == null) {
                return;
            }

            InputStream originalStream = assetResource.adaptTo(InputStream.class);
            if (originalStream == null) {
                return;
            }

            // Generate custom rendition (300x200 PNG)
            InputStream newRenditionStream = generateCustomRendition(originalStream);

            // Save rendition
            Node assetParentNode = session.getNode(assetPath + "/jcr:content/renditions");
            if (assetParentNode != null) {
                Node imageNode = assetParentNode.addNode("image.300.200.png", "nt:file");
                Node contentNode = imageNode.addNode("jcr:content", "nt:resource");

                ValueFactory valueFactory = session.getValueFactory();
                contentNode.setProperty("jcr:data", valueFactory.createBinary(newRenditionStream));
                session.save();
            }

        } catch (Exception e) {
            throw new WorkflowException("Error generating rendition: " + e.getMessage(), e);
        } finally {
            if (resolver != null) {
                resolver.close();
            }
        }
    }

    private InputStream generateCustomRendition(InputStream originalStream) throws Exception {
        BufferedImage originalImage = ImageIO.read(originalStream);

        // Resize Image
        BufferedImage resizedImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(originalImage, 0, 0, 300, 200, null);

        // Convert BufferedImage to InputStream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }
}
