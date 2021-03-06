package it.damore.app;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class Producer {

    protected final Logger log;

    protected Producer() {
        this.log = Logger.getLogger(getClass());
    }

    @Inject
    @Channel("from-producer-to-consumer1")
    Emitter<String> emitter;

    public void periodicallySendMessage() {
        AtomicInteger counter = new AtomicInteger();
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> {
                            String message = "Hello " + counter.getAndIncrement();
                            log.info("Emitting: " + message);
                            emitter.send(message);
                        },
                        1, 100, TimeUnit.MILLISECONDS);
    }

}
