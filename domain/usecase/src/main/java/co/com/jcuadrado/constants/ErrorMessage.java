package co.com.jcuadrado.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ErrorMessage {
    public static final String DOCUMENT_NUMBER_REQUIRED = "El número de documento es obligatorio";
    public static final String DOCUMENT_NUMBER_ALREADY_EXISTS = "El número de documento ya existe";
    public static final String NAME_REQUIRED = "El nombre es obligatorio";
    public static final String LAST_NAME_REQUIRED = "El apellido es obligatorio";
    public static final String EMAIL_REQUIRED = "El correo es obligatorio";
    public static final String EMAIL_ALREADY_EXISTS = "El correo ya existe";
    public static final String BASE_SALARY_REQUIRED = "El salario es obligatorio";
    public static final String BASE_SALARY_RANGE="El salario debe estar entre 1 y 15,000,000";
    public static final String ERROR_GETTING_USERS = "Error obteniendo los usuarios";
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String PASSWORD_REQUIRED = "La contraseña es obligatoria";
    public static final String PASSWORD_MIN_LENGTH = "La contraseña debe tener al menos 8 caracteres";
    public static final String INVALID_CREDENTIALS = "Credenciales inválidas";
    public static final String ROLE_DOES_NOT_EXIST = "El rol no existe";
}
