package co.com.jcuadrado.api.exception.handler;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Interface que define el contrato para manejar excepciones específicas
 * Cumple con el Dependency Inversion Principle (DIP)
 */
public interface ExceptionHandler<T extends Throwable> {

    /**
     * Verifica si este handler puede manejar la excepción dada
     */
    boolean canHandle(Throwable throwable);

    /**
     * Maneja la excepción y retorna la respuesta apropiada
     */
    Mono<Void> handle(ServerWebExchange exchange, T throwable);

    /**
     * Retorna el orden de prioridad del handler (menor número = mayor prioridad)
     */
    default int getOrder() {
        return 0;
    }
}