package it.damore.app;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class Processor2 {

    protected final Logger log;

    protected Processor2() {
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-consumer2-to-processor2")
    @Outgoing("from-processor2-to-consumer3")
    public Multi<String> consumeMulti2Multi(Multi<String> stream) {
        return stream
                .onItem()
                .transformToUni(msg -> {
                    String manipulated = String.format("XXX %s", msg);
                    log.infof("processor2 manipulated message %s", manipulated);
                    return Uni.createFrom().item(manipulated);
                }).concatenate();
    }

}
