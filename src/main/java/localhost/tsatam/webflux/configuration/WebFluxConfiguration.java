package localhost.tsatam.webflux.configuration;

import localhost.tsatam.webflux.motivation.MotivationHandler;
import localhost.tsatam.webflux.todo.TodoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebFluxConfiguration implements WebFluxConfigurer {
    @Bean
    public RouterFunction<ServerResponse> todoRouterFunction(TodoHandler handler) {
        return route()
            .path("/fn/todo", builder -> builder.nest(
                accept(MediaType.APPLICATION_JSON),
                builder2 -> builder2
                    .GET("", handler::listTodos)
                    .GET("/{id}", handler::getTodo)
                    .POST("", handler::addTodo)
            ))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> motivationRouterFunction(MotivationHandler handler) {
        return route()
            .GET("/motivation", handler::getMotivation)
            .build();
    }
}
