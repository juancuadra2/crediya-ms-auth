package co.com.jcuadrado.util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ObjectsUtilTest {

    @Test
    @DisplayName("Test if object is null")
    void objectIsNull() {
        assertTrue(ObjectsUtil.isNullOrEmpty(null));
    }

    @Test
    @DisplayName("Test if object is not null")
    void objectIsNotNull() {
        assertTrue(!ObjectsUtil.isNullOrEmpty(new Object()));
    }

    @Test
    @DisplayName("Test if string is empty")
    void stringIsEmpty() {
        String value = "";
        assertTrue(ObjectsUtil.isNullOrEmpty(value));
    }

    @Test
    @DisplayName("Test if string is not empty")
    void stringIsNotEmpty() {
        String value = "1";
        assertTrue(!ObjectsUtil.isNullOrEmpty(value));
    }

    @Test
    @DisplayName("Test if collection is empty")
    void testCollectionIsEmpty() {
        List<String> list = List.of();
        assertTrue(ObjectsUtil.isNullOrEmpty(list));
    }

    @Test
    @DisplayName("Test if collection is not empty")
    void testCollectionIsNotEmpty() {
        List<String> list = List.of("1");
        assertTrue(!ObjectsUtil.isNullOrEmpty(list));
    }

    @Test
    @DisplayName("Test if array is empty")
    void arrayIsEmpty() {
        String[] array = new String[0];
        assertTrue(ObjectsUtil.isNullOrEmpty(array));
    }

    @Test
    @DisplayName("Test if array is not empty")
    void arrayIsNotEmpty() {
        String[] array = new String[]{"1"};
        assertTrue(!ObjectsUtil.isNullOrEmpty(array));
    }
}
