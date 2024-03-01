package io.weli.jberet.se.simple;

import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.runtime.BatchStatus;
import jakarta.inject.Named;

@Named("simpleBatchlet")
public class SimpleBatchlet extends AbstractBatchlet {

    @Override
    public String process() throws Exception {
        return BatchStatus.COMPLETED.toString();
    }
}
