package co.com.menu.reactive.api.MenuReactivveAPI.repository;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuRepository extends ReactiveMongoRepository<Menu, String> {
}
