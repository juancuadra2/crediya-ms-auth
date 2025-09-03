package co.com.jcuadrado.api.constant.doc;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class UserDtoConstants {

    public static final String DOCUMENT_NUMBER_DESCRIPTION = "Número de documento del usuario";
    public static final String NAME_DESCRIPTION = "Nombre del usuario";
    public static final String LAST_NAME_DESCRIPTION = "Apellido del usuario";
    public static final String EMAIL_DESCRIPTION = "Correo electrónico del usuario";
    public static final String PHONE_DESCRIPTION = "Número de teléfono del usuario";
    public static final String ADDRESS_DESCRIPTION = "Dirección de residencia del usuario";
    public static final String BIRTH_DATE_DESCRIPTION = "Fecha de nacimiento del usuario";
    public static final String ROLE_DESCRIPTION = "Rol del usuario en el sistema";
    public static final String BASE_SALARY_DESCRIPTION = "Salario base del usuario";

    public static final int NAME_MIN_LENGTH = 2;
    public static final int NAME_MAX_LENGTH = 50;
    public static final int LAST_NAME_MIN_LENGTH = 2;
    public static final int LAST_NAME_MAX_LENGTH = 50;
    public static final int ADDRESS_MAX_LENGTH = 255;

    public static final String BASE_SALARY_MINIMUM = "0.0";
    public static final String BASE_SALARY_MINIMUM_EXAMPLE = "0.01";

    public static final String PHONE_PATTERN = "^\\+57[0-9]{10}$";

    public static final String DOCUMENT_NUMBER_EXAMPLE = "12345678";
    public static final String NAME_EXAMPLE = "Juan";
    public static final String LAST_NAME_EXAMPLE = "Pérez";
    public static final String EMAIL_EXAMPLE = "juan.perez@example.com";
    public static final String PHONE_EXAMPLE = "+573001234567";
    public static final String ADDRESS_EXAMPLE = "Calle 123 #45-67";
    public static final String BIRTH_DATE_EXAMPLE = "1990-01-15";
    public static final String ROLE_EXAMPLE = "ADMIN";
    public static final String BASE_SALARY_EXAMPLE = "2500000.0";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
}
