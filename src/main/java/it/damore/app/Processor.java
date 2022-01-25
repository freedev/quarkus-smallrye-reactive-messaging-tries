package it.damore.app;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Processor {

    protected final Logger log;

    BroadcastProcessor<String> messages;

    protected Processor() {
        this.messages = BroadcastProcessor.create();
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-single-to-multi")
    @Outgoing("from-multi-to-multi")
    public Multi<Message<String>> consumeMulti2Multi(Multi<Message<String>> stream) {
        return stream.onItem()
                .transform(s -> s.withPayload(String.format("manipulated %s", s)));
    }

}
