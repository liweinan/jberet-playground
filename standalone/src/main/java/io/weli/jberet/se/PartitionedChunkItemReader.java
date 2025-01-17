package io.weli.jberet.se;

import jakarta.batch.api.BatchProperty;
import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

// https://learning.oreilly.com/library/view/java-ee-7/9781449370589/ch15.html#I_sect115_id290992
@Named("partitionedChunkReader")
public class PartitionedChunkItemReader extends AbstractItemReader {
    private Integer[] tokens;
    private Integer count;

    @Inject
    JobContext jobContext;

    @Inject
    @BatchProperty(name = "start")
    private String start;

    @Inject
    @BatchProperty(name = "end")
    private String end;

    @Override
    public Integer readItem() throws Exception {
        if (count >= tokens.length) {
            return null;
        }

        System.out.println("jobContext: " + jobContext + ", count: " + count);
        jobContext.setTransientUserData(count);
        return tokens[count++];
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        System.out.println("START -> " + start);
        if (Integer.parseInt(start) == 1) {
            tokens = new Integer[]{1, 2, 3, 4, 5};
        } else {
            tokens = new Integer[]{6, 7, 8, 9, 10};
        }
        count = 0;

    }

}
