package co.com.jcuadrado.model.user.gateways;

public interface PasswordEncoderGateway {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
