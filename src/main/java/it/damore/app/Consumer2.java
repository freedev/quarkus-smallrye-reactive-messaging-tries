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
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class Consumer2 {

    protected final Logger log;

    protected Consumer2() {
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-multi-to-multi")
    public Uni<Void> consume(Message<String> message) {
            log.info("consuming message from from-single-to-multi " + message.getPayload());
        return Uni.createFrom().voidItem();
    }

//    @Incoming("multi-manifest-response")
//    public Uni<Void> producer(Multi<Message<String>> stream) {
//        stream.group()
//                .intoLists().of(50, Duration.ofSeconds(1))
//                .invoke(list -> {
//                    log.info(list.size());
//                })
//                .flatMap(list -> Multi.createFrom().items(list.stream()))
//                .subscribe().with(msg -> log.info(msg.getPayload()));
//        return Uni.createFrom().voidItem();
//    }

}
