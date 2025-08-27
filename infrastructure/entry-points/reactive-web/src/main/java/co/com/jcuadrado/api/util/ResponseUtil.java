package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.SuccessStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public final class ResponseUtil {

    public static <T> Mono<ServerResponse> buildSuccessResponse(T dto, SuccessStatus status) {
        return ServerResponse
                .status(status.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto);
    }

}
