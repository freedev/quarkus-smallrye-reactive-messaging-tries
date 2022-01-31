package it.damore.app;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Consumer2 {

    protected final Logger log;

    BroadcastProcessor<String> processor;

    protected Consumer2() {
        this.processor = BroadcastProcessor.create();
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-processor1-to-consumer2")
    @Outgoing("from-consumer2-to-processor2")
    public Multi<String> sendToProcessor(Multi<String> stream) {
        return stream.onItem().invoke(m -> {
            log.infof("consumer2 do something with %s", m);
        });
    }

}
