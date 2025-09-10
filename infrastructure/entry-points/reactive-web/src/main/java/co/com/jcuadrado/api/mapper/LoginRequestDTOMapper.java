package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.auth.LoginRequestDTO;
import co.com.jcuadrado.model.auth.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginRequestDTOMapper {
    Login toModel(LoginRequestDTO loginRequestDTO);
}
