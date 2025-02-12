package com.mynewproject.core.workflow.process;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service = ParticipantStepChooser.class, property = { "chooser.label=" + "Dynamic Participant Workflow"
})
public class DynamicAssigneeHandler implements ParticipantStepChooser {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        String contentPath = workItem.getContentPath();
        if (contentPath.contains("fr-fr")) {
            log.info("Entering DynamicParticipantStep Assinged to User1 >>>>>> ");
            return "user1"; // Assign to a specific user/group
        } else if (contentPath.contains("en")) {
            log.info("Entering DynamicParticipantStep Assinged to User2 ****** ");

            return "user2";
        }
        log.info("Entering DynamicParticipantStep Assinged to Admin +++++++++ ");
        return "admin";
    }

}
