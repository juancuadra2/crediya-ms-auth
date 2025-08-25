package co.com.jcuadrado.r2dbc.repository.role;

import co.com.jcuadrado.r2dbc.entity.RoleEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RoleReactiveRepository extends ReactiveCrudRepository<RoleEntity, String>, ReactiveQueryByExampleExecutor<RoleEntity> {

}
