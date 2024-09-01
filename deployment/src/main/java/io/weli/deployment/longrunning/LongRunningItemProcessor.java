package io.weli.deployment.longrunning;

import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.inject.Named;

@Named("longRunningItemProcessor")
public class LongRunningItemProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object obj) throws Exception {
        System.out.println("Processing: " + obj);
        Thread.sleep(1500);
        return obj;
    }
}
