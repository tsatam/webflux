package localhost.tsatam.webflux.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class FluxTest {
    @Test
    @DisplayName("A Flux<T> can represent 0-n values. ")
    void fluxCanRepresentZeroToNValues() {
        var empty = Flux.just();
        StepVerifier.create(empty)
            .expectComplete()
            .verify();

        var oneValue = Flux.just("Hello");
        StepVerifier.create(oneValue)
            .expectNext("Hello")
            .expectComplete()
            .verify();

        var manyValues = Flux.just("Hello", " ", "World", ".");
        StepVerifier.create(manyValues)
            .expectNext("Hello")
            .expectNext(" ")
            .expectNext("World")
            .expectNext(".")
            .expectComplete()
            .verify();
    }

    @Test
    @DisplayName("A Flux<T> can emit an error. ")
    void fluxCanError() {
        var error = Flux.error(RuntimeException::new);

        StepVerifier.create(error)
            .expectError(RuntimeException.class)
            .verify();
    }

    @Test
    @DisplayName("A Flux<T>'s intermediate operations can return a Mono<U>")
    void fluxCanTransformIntoMono() {
        Mono<String> transformedToMono = Flux.just("Hello", " ", "World", ".")
            .reduce(String::concat);

        StepVerifier.create(transformedToMono)
            .expectNext("Hello World.")
            .expectComplete()
            .verify();
    }

    @Test
    @DisplayName("A Flux<T> is similar to a Stream<T>. ")
    void fluxIsSimilarToStream() {
        var baseValue = Flux.just("Hello", " ", "World", ".");

        var processedAsFlux = baseValue
            .reduce(String::concat)
            .block();

        var processedAsStream = baseValue
            .toStream()
            .reduce(String::concat)
            .get();

        assertThat(processedAsFlux).isEqualTo(processedAsStream);
    }
}
