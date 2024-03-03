package io.weli.jberet.se;

import jakarta.batch.api.chunk.AbstractCheckpointAlgorithm;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class CustomCheckPoint extends AbstractCheckpointAlgorithm {

    @Inject
    JobContext jobContext;

    @Override
    public boolean isReadyToCheckpoint() throws Exception {
        int counterRead = (Integer) jobContext.getTransientUserData();
        return counterRead % 3 == 0;
    }
}
