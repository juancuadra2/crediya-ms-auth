package co.com.jcuadrado.api.constant.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Patrones de expresiones regulares para validación
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexPatterns {
    
    public static final String PHONE = "^\\+?[1-9]\\d{1,14}$";
    public static final String EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String DOCUMENT_NUMBER = "^[0-9]{8,12}$";
}
