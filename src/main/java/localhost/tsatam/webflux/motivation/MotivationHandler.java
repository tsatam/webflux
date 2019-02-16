package localhost.tsatam.webflux.motivation;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class MotivationHandler {
    private WebClient client;

    public MotivationHandler(WebClient client) {
        this.client = client;
    }

    public Mono<ServerResponse> getMotivation(ServerRequest request) {
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Flux.range(0, 10)
                .flatMap(f -> getWisdom()), WisdomResponse.class);
    }

    private Mono<WisdomResponse> getWisdom() {
        return client.get()
            .uri("http://upwise.cfapps.io/wisdom/random")
            .retrieve()
            .bodyToMono(WisdomResponse.class)
            .delayElement(Duration.ofSeconds(5));
    }
}
