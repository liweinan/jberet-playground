package io.weli.jberet;

import jakarta.batch.api.chunk.AbstractItemWriter;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

@Named("writer")
public class SimpleChunkWriter extends AbstractItemWriter {
    List<Integer> processed = new ArrayList<>();

    @Override
    public void writeItems(List<Object> items) throws Exception {
        items.stream().map(Integer.class::cast).forEach(processed::add);
        System.out.println("items -> " + items);
    }
}
