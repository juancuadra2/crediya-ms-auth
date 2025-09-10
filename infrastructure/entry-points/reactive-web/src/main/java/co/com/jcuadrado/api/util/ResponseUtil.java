package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.SuccessStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseUtil {

    public static <T> Mono<ServerResponse> buildSuccessResponse(T dto, SuccessStatus status) {
        return ServerResponse
                .status(status.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto);
    }

}
