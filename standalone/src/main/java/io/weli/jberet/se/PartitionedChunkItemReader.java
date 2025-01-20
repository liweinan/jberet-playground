package io.weli.jberet.se;

import jakarta.batch.api.BatchProperty;
import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

// https://learning.oreilly.com/library/view/java-ee-7/9781449370589/ch15.html#I_sect115_id290992
@Named("partitionedChunkReader")
public class PartitionedChunkItemReader extends AbstractItemReader {
    private Integer[] tokens;
    private Integer count;

    @Inject
    JobContext jobContext;

    @Inject
    @BatchProperty(name = "isFirstPartition")
    private boolean isFirstPartition;

//    @Inject
//    @BatchProperty(name = "endVal")
//    private String end;

    @Override
    public Integer readItem() throws Exception {
        if (count >= tokens.length) {
            return null;
        }

//        System.out.println("jobContext: " + jobContext + ", count: " + count);
        // could be used in place like `CustomCheckPoint`
        jobContext.setTransientUserData(count);
        var item = tokens[count++];
        System.out.println("read item: " + item);
        return item;
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        System.out.println("isFirstPartition -> " + isFirstPartition);
        if (isFirstPartition) {
            tokens = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
            System.out.println("tokens -> " + Arrays.stream(tokens).map(String::valueOf).collect(Collectors.toList()));
        } else {
            tokens = new Integer[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        }
        count = 0;

    }

}
