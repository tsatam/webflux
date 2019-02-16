package localhost.tsatam.webflux.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.function.DatabaseClient;

import javax.annotation.PostConstruct;

@Configuration
public class DatabaseConfiguration {
    private DatabaseClient client;

    public DatabaseConfiguration(DatabaseClient client) {
        this.client = client;
    }

    @PostConstruct
    public void setUpTables() {
        client.execute()
            .sql("CREATE TABLE IF NOT EXISTS todo" +
                "(id IDENTITY PRIMARY KEY," +
                "name VARCHAR(255)," +
                "is_completed BOOLEAN)")
            .fetch()
            .rowsUpdated()
            .block();
    }
}
