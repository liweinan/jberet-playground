package io.weli.deployment.longrunning;

import java.io.Serializable;

import jakarta.batch.api.BatchProperty;
import jakarta.batch.api.chunk.ItemReader;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("longRunningItemReader")
public class LongRunningItemReader implements ItemReader {
    private int counter = 0;

    @Inject
    @BatchProperty
    private int start;

    @Inject
    @BatchProperty
    private int stop;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        if (checkpoint != null) {
            counter = (Integer) checkpoint;
        } else {
            counter = start;
        }

        if (start > stop) {
            throw new IllegalArgumentException("start > stop");
        }
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Object readItem() throws Exception {
        if (counter >= stop) {
            return null;
        }
        return counter++;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return counter;
    }
}
