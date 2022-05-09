package com.xendv.ReportLoader;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    //private static final Logger log = (Logger) LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Autowired
    public JobCompletionNotificationListener() {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            //log.info("!!! JOB FINISHED! Time to verify the results");
            System.out.println("!!! JOB FINISHED! Time to verify the results");
        }
    }
}