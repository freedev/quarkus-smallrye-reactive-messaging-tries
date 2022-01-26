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
public class Consumer3 {

    protected final Logger log;

    protected Consumer3() {
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-processor2-to-consumer3")
    public CompletionStage<Void> consume(String msg) {
        return Uni.createFrom().item(msg)
                .onItem().invoke(m -> log.info("message arrived"))
                .replaceWithVoid()
                .subscribeAsCompletionStage();
    }

}
