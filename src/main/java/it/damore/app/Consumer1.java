package it.damore.app;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.Executors;

@ApplicationScoped
public class Consumer1 {

    protected final Logger log;

    BroadcastProcessor<String> processor;

    protected Consumer1() {
        this.processor = BroadcastProcessor.create();
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-producer-to-consumer1")
    public Uni<Void> sendToProcessor(String message) {
        log.info("Consumer1 - read from producer -> processor.onNext for msg " + message);
        processor.onNext(message);
        return Uni.createFrom().voidItem();
    }

    @Outgoing("from-consumer1-to-processor1")
    public Multi<String> produceMulti() {
        return Multi.createFrom()
                .publisher(processor)
                .onItem()
                .invoke(msg -> {
                    log.info("Consumer1 - created publisher and calling invoke for msg " + msg);
                })
                .emitOn(Executors.newSingleThreadExecutor());
    }
}
