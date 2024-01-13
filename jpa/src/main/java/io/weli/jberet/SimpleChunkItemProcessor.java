package io.weli.jberet;

import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.inject.Named;

@Named("itemProcessor")
public class SimpleChunkItemProcessor implements ItemProcessor {
    @Override
    public Integer processItem(Object t) {
        System.out.println("processing item -> " + t);
        Integer item = (Integer) t;
//        return item % 2 == 0 ? item : null;
        return item;
    }
}