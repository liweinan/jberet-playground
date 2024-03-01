package io.weli.jberet.simple;

import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.runtime.BatchStatus;
import jakarta.inject.Named;

@Named("simpleBatchlet")
public class SimpleBatchLet extends AbstractBatchlet {

    @Override
    public String process() throws Exception {
        return BatchStatus.COMPLETED.toString();
    }
}
