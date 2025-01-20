package io.weli.jberet.se.simple;

import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.inject.Named;

import java.util.Random;

import static io.weli.jberet.Commons.sleepRandomSeconds;

@Named("itemProcessor")
public class SimpleChunkItemProcessor implements ItemProcessor {

    @Override
    public Integer processItem(Object t) {
        sleepRandomSeconds(5);
        System.out.println("processing item -> " + t);
        Integer item = (Integer) t;
//        return item % 2 == 0 ? item : null;
        return item;
    }



}