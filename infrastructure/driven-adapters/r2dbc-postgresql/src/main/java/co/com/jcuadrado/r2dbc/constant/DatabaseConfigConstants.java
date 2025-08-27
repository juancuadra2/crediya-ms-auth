package co.com.jcuadrado.r2dbc.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class DatabaseConfigConstants {
    public static final int INITIAL_SIZE = 12;
    public static final int MAX_SIZE = 15;
    public static final int MAX_IDLE_TIME = 30;
    public static final int DEFAULT_PORT = 5432;
    public static final String VALIDATION_QUERY = "SELECT 1";
    public static final String POOL_CONFIG_NAME = "api-postgres-connection-pool";
}
