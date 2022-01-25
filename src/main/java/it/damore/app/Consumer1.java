package it.damore.app;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class Consumer1 {

    protected final Logger log;

    BroadcastProcessor<Message<String>> messages;

    protected Consumer1() {
        this.messages = BroadcastProcessor.create();
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("hello-channel")
    public Uni<Void> consume(Message<String> message) {
        log.info("consume from hello-channel");
        messages.onNext(message);
        return Uni.createFrom().voidItem();
    }

    @Outgoing("from-single-to-multi")
    public Multi<Message<String>> produceMulti() {
        return Multi.createFrom().publisher(messages);
    }
}
