package io.weli.jberet;

import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("itemReader")
public class SimpleChunkItemReader extends AbstractItemReader {
    private Integer[] tokens;
    private Integer count;

    @Inject
    JobContext jobContext;

    @Override
    public Integer readItem() throws Exception {
        if (count >= tokens.length) {
            return null;
        }

        jobContext.setTransientUserData(count);
        return tokens[count++];
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        tokens = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        count = 0;
    }

}
