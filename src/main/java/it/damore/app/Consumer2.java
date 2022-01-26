package it.damore.app;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class Consumer2 {

    protected final Logger log;

    BroadcastProcessor<String> processor;

    protected Consumer2() {
        this.processor = BroadcastProcessor.create();
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-processor-to-consumer2")
    public Uni<Void> sendToProcessor(String message) {
        log.info("consume from hello-channel");
        processor.onNext(message);
        return Uni.createFrom().voidItem();
    }

    @Outgoing("from-consumer2-to-processor2")
    public Multi<String> produceMulti() {
        return Multi.createFrom()
                .publisher(processor);
    }

}
