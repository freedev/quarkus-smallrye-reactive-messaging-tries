package it.damore.app;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Processor1 {

    protected final Logger log;

    BroadcastProcessor<String> messages;

    protected Processor1() {
        this.messages = BroadcastProcessor.create();
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-consumer1-to-processor1")
    @Outgoing("from-processor-to-consumer2")
    public Multi<String> consumeMulti2Multi(Multi<String> stream) {
        return stream
                .onItem()
                .transform(msg -> {
                    String manipulated = String.format("manipulated %s", msg);
                    log.infof("manipulated message %s", manipulated);
                    return manipulated;
                });
    }

}
