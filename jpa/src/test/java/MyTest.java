//import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.JobExecution;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.SharedCacheMode;
//import jakarta.persistence.spi.PersistenceUnitTransactionType;
//import org.h2.jdbcx.JdbcDataSource;
//import org.hibernate.jpa.boot.spi.Bootstrap;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
//import org.jberet.jpa.repository.JpaRepository;
//import org.jberet.jpa.repository.entity.JobExecutionJpa;
//import org.jberet.jpa.repository.entity.JobInstanceJpa;
//import org.jberet.jpa.repository.entity.PartitionExecutionJpa;
//import org.jberet.jpa.repository.entity.StepExecutionJpa;
//import org.jberet.jpa.util.BatchPersistenceUnitInfo;
import org.jberet.operations.DelegatingJobOperator;
import org.jberet.operations.JobOperatorImpl;
import org.jberet.repository.JobRepository;
//import org.jberet.se.BatchSEEnvironment;
import org.junit.jupiter.api.Test;

//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Properties;
//import java.util.stream.Collectors;

import static jakarta.batch.runtime.BatchStatus.COMPLETED;
import static java.lang.Thread.sleep;
//import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
//import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTest {

    private static final int MAX_TRIES = 40;
    private static final int THREAD_SLEEP = 1000;

//    private static JobRepository repo;
//
//    private static EntityManagerFactory entityManagerFactory;
//
//    private static EntityManagerFactoryBuilder entityManagerFactoryBuilder;
//    private static EntityManager entityManager;

//
//    public void setupDb() {
//        JdbcDataSource ds = new JdbcDataSource();
//        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//
//        BatchPersistenceUnitInfo batchPersistenceUnitInfo = new BatchPersistenceUnitInfo();
//        batchPersistenceUnitInfo.setPersistenceUnitName(MyTest.class.getSimpleName());
//        batchPersistenceUnitInfo.setClassLoader(Thread.currentThread().getContextClassLoader());
//        batchPersistenceUnitInfo.setProperties(new Properties());
//        batchPersistenceUnitInfo.setNonJtaDataSource(ds);
//        batchPersistenceUnitInfo.setTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
//        batchPersistenceUnitInfo.setSharedCacheMode(SharedCacheMode.ALL);
//        batchPersistenceUnitInfo.setExcludeUnlistedClasses(false);
//        batchPersistenceUnitInfo.setManagedClassNames(
//                Arrays.asList(
//                        JobInstanceJpa.class,
//                        JobExecutionJpa.class,
//                        StepExecutionJpa.class,
//                        PartitionExecutionJpa.class
//                ).stream().map(
//                        c -> c.getCanonicalName()
//                ).collect(Collectors.toList())
//        );
//
//        Map integration = new HashMap<>();
//        integration.put(HBM2DDL_AUTO, "create");
//        integration.put(SHOW_SQL, "true");
//
//        entityManagerFactoryBuilder = Bootstrap.getEntityManagerFactoryBuilder(
//                batchPersistenceUnitInfo,
//                integration
//        );
//
//        entityManagerFactory = entityManagerFactoryBuilder.build();
//        entityManager = entityManagerFactory.createEntityManager();
//
//        repo = new JpaRepository(entityManager);
//    }

//    @Test
    public void givenChunk_thenBatch_completesWithSuccess() throws Exception {

//        setupDb();

        JobOperatorImpl jobOperator = (JobOperatorImpl) ((DelegatingJobOperator) BatchRuntime.getJobOperator()).getDelegate();
//        var env = (BatchSEEnvironment) jobOperator.getBatchEnvironment();
//        env.setJobRepository(repo);


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
