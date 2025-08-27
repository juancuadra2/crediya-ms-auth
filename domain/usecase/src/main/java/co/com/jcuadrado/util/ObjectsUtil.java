package co.com.jcuadrado.util;

import java.util.Collection;

public class ObjectsUtil {

    public static boolean isNullOrEmpty(Object value) {
        return switch (value) {
            case null -> true;
            case String stringValue -> stringValue.trim().isEmpty();
            case Collection<?> collection -> collection.isEmpty();
            case Object[] array -> array.length == 0;
            default -> false;
        };
    }

}
