package io.weli.jberet;

import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.JobExecution;
import jakarta.ws.rs.Path;

import java.util.Properties;

import static jakarta.batch.runtime.BatchStatus.COMPLETED;
import static java.lang.Thread.sleep;

@Path("/batch")
public class RESTApi {
    @Path("/start")
    public String start() throws InterruptedException {
        System.out.println("batch start");
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long executionId = jobOperator.start("simpleBatchletJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = keepTestAlive(jobExecution);
        return jobExecution.getBatchStatus().toString();

    }

    private static final int MAX_TRIES = 40;
    private static final int THREAD_SLEEP = 1000;

    private JobExecution keepTestAlive(JobExecution jobExecution) throws InterruptedException {
        int maxTries = 0;
        while (!jobExecution.getBatchStatus().equals(COMPLETED)) {
            if (maxTries < MAX_TRIES) {
                maxTries++;
                sleep(THREAD_SLEEP);
                jobExecution = BatchRuntime.getJobOperator().getJobExecution(jobExecution.getExecutionId());
            } else {
                break;
            }
        }
        sleep(THREAD_SLEEP);
        return jobExecution;
    }

}
