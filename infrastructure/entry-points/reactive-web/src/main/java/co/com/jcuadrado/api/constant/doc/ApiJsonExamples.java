package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public final class ApiJsonExamples {

    public static final String CREATE_USER_REQUEST_EXAMPLE = """
        {
          "documentNumber": "12345678",
          "name": "Juan",
          "lastName": "Pérez",
          "email": "juan.perez@example.com",
          "phone": "+573001234567",
          "address": "Calle 123 #45-67",
          "birthDate": "1990-01-15",
          "role": "ADMIN",
          "baseSalary": 2500000.0
        }
        """;
    
    public static final String USER_CREATED_RESPONSE_EXAMPLE = """
        {
          "documentNumber": "12345678",
          "name": "Juan",
          "lastName": "Pérez",
          "email": "juan.perez@example.com",
          "phone": "+573001234567",
          "address": "Calle 123 #45-67",
          "birthDate": "1990-01-15",
          "baseSalary": 2500000.0,
          "role": "ADMIN"
        }
        """;

    public static final String USERS_LIST_RESPONSE_EXAMPLE = """
        [
          {
            "documentNumber": "12345678",
            "name": "Juan",
            "lastName": "Pérez",
            "email": "juan.perez@example.com",
            "phone": "+573001234567",
            "address": "Calle 123 #45-67",
            "birthDate": "1990-01-15",
            "baseSalary": 2500000.0,
            "role": "ADMIN"
          },
          {
            "documentNumber": "87654321",
            "name": "María",
            "lastName": "García",
            "email": "maria.garcia@example.com",
            "phone": "+573009876543",
            "address": "Carrera 45 #23-89",
            "birthDate": "1985-05-20",
            "baseSalary": 3000000.0,
            "role": "USER"
          }
        ]
        """;
    
    public static final String VALIDATION_ERROR_RESPONSE_EXAMPLE = """
        {
          "messages": [
            "El email debe tener un formato válido",
            "El número de teléfono debe seguir el patrón válido"
          ],
          "error": "Validation Error",
          "status": "BAD_REQUEST"
        }
        """;

    public static final String INTERNAL_SERVER_ERROR_RESPONSE_EXAMPLE = """
        {
          "messages": ["Error interno del servidor"],
          "error": "Internal Server Error",
          "status": "INTERNAL_SERVER_ERROR"
        }
        """;

    // Auth examples
    public static final String LOGIN_REQUEST_EXAMPLE = """
        {
          "email": "admin@example.com",
          "password": "admin123"
        }
        """;

    public static final String LOGIN_RESPONSE_EXAMPLE = """
        {
          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
          "email": "admin@example.com",
          "role": "ADMIN"
        }
        """;

    public static final String VALIDATE_TOKEN_RESPONSE_EXAMPLE = """
        {
          "email": "admin@example.com",
          "role": "ADMIN"
        }
        """;

    public static final String UNAUTHORIZED_ERROR_RESPONSE_EXAMPLE = """
        {
          "messages": ["Credenciales inválidas"],
          "error": "Unauthorized",
          "status": "UNAUTHORIZED"
        }
        """;
}
