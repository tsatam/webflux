package localhost.tsatam.webflux.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.ProxyProvider;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
            .tcpConfiguration(tcpClient -> tcpClient.proxy(WebClientConfiguration::proxyConfig));
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder().clientConnector(connector).build();
    }

    private static void proxyConfig(ProxyProvider.TypeSpec proxy) {
        proxy
            .type(ProxyProvider.Proxy.HTTP)
            .host("internet.ford.com")
            .port(83);
    }
}
