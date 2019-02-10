package localhost.tsatam.webflux.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class MonoTest {

    @Test
    @DisplayName("A Mono<T> can represent a single value. ")
    void monoCanRepresentsSingleValue() {
        Mono<String> withValue = Mono.just("Hello world");

        StepVerifier.create(withValue)
            .expectNext("Hello world")
            .expectComplete()
            .verify();
    }

    @Test
    @DisplayName("A Mono<T> can represent no value. ")
    void monoCanRepresentNoValue() {
        var noValue = Mono.empty();

        StepVerifier.create(noValue)
            .expectComplete()
            .verify();
    }

    @Test
    @DisplayName("A Mono<T> can emit an error. ")
    void monoCanError() {
        var error = Mono.error(RuntimeException::new);

        StepVerifier.create(error)
            .expectError(RuntimeException.class)
            .verify();
    }

    @Test
    @DisplayName("A Mono<T> is similar to an Optional<T>")
    void monoIsSimilarToOptional() {
        var baseValue = Mono.just("Hello world");

        Function<String, String> mapper = value -> String.format("The Original String is [%s]", value);

        String processedAsMono = baseValue
            .map(String::toUpperCase)
            .map(mapper)
            .block();

        String processedAsOptional = baseValue
            .blockOptional()
            .map(String::toUpperCase)
            .map(mapper)
            .get();

        assertThat(processedAsMono).isEqualTo(processedAsOptional);
    }
}
