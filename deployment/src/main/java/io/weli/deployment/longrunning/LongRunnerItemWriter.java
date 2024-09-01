package io.weli.deployment.longrunning;

import java.io.Serializable;
import java.util.List;

import jakarta.batch.api.chunk.ItemWriter;
import jakarta.inject.Named;

@Named("longRunningItemWriter")
public class LongRunnerItemWriter implements ItemWriter {

    @Override
    public void open(Serializable checkpoint) throws Exception {
        System.out.println(getClass().getName() + ".open(" + checkpoint + ")");
    }

    @Override
    public void close() throws Exception {
        System.out.println(getClass().getName() + ".close()");
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {
        System.out.println("----- Chunk, length = " + items.size() + " -----");
        items.forEach(System.out::println);
        System.out.println("----- Chunk end -----");
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        System.out.println(getClass().getName() + ".checkpointInfo()");
        return null;
    }
}
