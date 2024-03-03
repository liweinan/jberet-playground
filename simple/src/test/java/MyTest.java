import jakarta.batch.operations.JobOperator;
import jakarta.batch.operations.JobRestartException;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.JobExecution;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static jakarta.batch.runtime.BatchStatus.COMPLETED;
import static jakarta.batch.runtime.BatchStatus.STARTED;
import static jakarta.batch.runtime.BatchStatus.STOPPED;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyTest {

    private static final int MAX_TRIES = 40;
    private static final int THREAD_SLEEP = 1000;

    @Test
    public void givenChunk_thenBatch_completesWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long executionId = jobOperator.start("simpleChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = keepTestAlive(jobExecution, COMPLETED);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
    @Test
    @Disabled
    public void testCheckpoint() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();

        System.out.println("starting job...");
        long executionId = jobOperator.start("checkpointJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = keepTestAlive(jobExecution, STARTED);
        System.out.println("started");

        System.out.println("stopping job...");
        jobOperator.stop(executionId);
        jobExecution = keepTestAlive(jobExecution, STOPPED);
        System.out.println("stopped");


        System.out.println("restarting job...");
        jobOperator.restart(executionId, new Properties());
        jobExecution = keepTestAlive(jobExecution, STARTED);
        System.out.println("restarted");

//        jobExecution = keepTestAlive(jobExecution, COMPLETED);
//        assertEquals(BatchStatus.COMPLETED, jobExecution.getBatchStatus());
    }

    @Test
    public void givenPartition_thenBatch_completesWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long executionId = jobOperator.start("myPartitionJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = keepTestAlive(jobExecution, COMPLETED);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }

    @Test
    public void testRestart1() throws Exception {
        assertThrows(JobRestartException.class,
                () -> {
                    JobOperator jobOperator = BatchRuntime.getJobOperator();
                    long executionId = jobOperator.start("simpleBatchletJob", new Properties());
                    JobExecution jobExecution = jobOperator.getJobExecution(executionId);

                    // jakarta.batch.operations.JobRestartException: JBERET000647: Restarting job execution 5, job name simpleBatchletJob, batch status STARTED, restart mode null, but it seems the original execution is still alive.
                    jobOperator.restart(executionId, null);

                    jobExecution = keepTestAlive(jobExecution, COMPLETED);
                    assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
                });
    }



    private JobExecution keepTestAlive(JobExecution jobExecution, BatchStatus status) throws InterruptedException {
        int maxTries = 0;
        while (!jobExecution.getBatchStatus().equals(status)) {
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
