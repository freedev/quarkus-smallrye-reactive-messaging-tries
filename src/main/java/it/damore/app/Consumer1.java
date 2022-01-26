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
        log.info("consume from hello-channel");
        processor.onNext(message);
        return Uni.createFrom().voidItem();
    }

    @Outgoing("from-consumer1-to-processor1")
    public Multi<String> produceMulti() {
        return Multi.createFrom()
                .publisher(processor);
    }
}
