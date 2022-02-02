package it.damore.app;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Stream;

@ApplicationScoped
public class Processor1 {

    protected final Logger log;

    protected Processor1() {
        this.log = Logger.getLogger(getClass());
    }

    @Incoming("from-consumer1-to-processor1")
    @Outgoing("from-processor1-to-consumer2")
    public Multi<String> consumeMulti2Multi(Multi<String> stream) {
        return stream
                .group()
                .intoLists()
                .of(5)
                .flatMap(list -> {
                    log.info("processor1 receive group of " + list.size());
                    Stream<String> manipulatedStream = list.stream().map(msg -> {
                        String manipulated = String.format("YYY %s", msg);
                        log.infof("processor1 manipulated message %s", manipulated);
                        return manipulated;
                    });
                    return Multi.createFrom().items(manipulatedStream);
                })
//                .onItem()
//                .transformToUni(m -> Uni.createFrom().item(m))
//                .concatenate()
                ;
    }

}
