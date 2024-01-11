import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.JobExecution;
import org.jberet.operations.DelegatingJobOperator;
import org.jberet.operations.JobOperatorImpl;
import org.jberet.se.BatchSEEnvironment;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static jakarta.batch.runtime.BatchStatus.COMPLETED;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTest {

    private static final int MAX_TRIES = 40;
    private static final int THREAD_SLEEP = 1000;

    @Test
    public void givenChunk_thenBatch_completesWithSuccess() throws Exception {
        JobOperatorImpl jobOperator = (JobOperatorImpl) ((DelegatingJobOperator) BatchRuntime.getJobOperator()).getDelegate();
        var env = (BatchSEEnvironment) jobOperator.getBatchEnvironment();
        env.getJobRepository();

        long executionId = jobOperator.start("simpleChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = keepTestAlive(jobExecution);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }

//    @Test
//    public void givenBatchlet_thenBatch_completeWithSuccess() throws Exception {
//        JobOperator jobOperator = BatchRuntime.getJobOperator();
//        long executionId = jobOperator.start("simpleBatchletJob", new Properties());
//        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
//        jobExecution = keepTestAlive(jobExecution);
//        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
//    }
//
//    @Test
//    public void givenPartition_thenBatch_completesWithSuccess() throws Exception {
//        JobOperator jobOperator = BatchRuntime.getJobOperator();
//        long executionId = jobOperator.start("myPartitionJob", new Properties());
//        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
//        jobExecution = keepTestAlive(jobExecution);
//        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
//    }

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
