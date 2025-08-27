package co.com.jcuadrado.r2dbc.config;

import co.com.jcuadrado.r2dbc.constant.DatabaseConfigConstants;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class PostgreSQLConnectionPool {

	@Bean
	public ConnectionPool getConnectionConfig(PostgresqlConnectionProperties properties) {
		PostgresqlConnectionConfiguration dbConfiguration = PostgresqlConnectionConfiguration.builder()
                .host(properties.host())
                .port(properties.port())
                .database(properties.database())
                .schema(properties.schema())
                .username(properties.username())
                .password(properties.password())
                .build();

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(new PostgresqlConnectionFactory(dbConfiguration))
                .name(DatabaseConfigConstants.POOL_CONFIG_NAME)
                .initialSize(DatabaseConfigConstants.INITIAL_SIZE)
                .maxSize(DatabaseConfigConstants.MAX_SIZE)
                .maxIdleTime(Duration.ofMinutes(DatabaseConfigConstants.MAX_IDLE_TIME))
                .validationQuery(DatabaseConfigConstants.VALIDATION_QUERY)
                .build();

		return new ConnectionPool(poolConfiguration);
	}
}