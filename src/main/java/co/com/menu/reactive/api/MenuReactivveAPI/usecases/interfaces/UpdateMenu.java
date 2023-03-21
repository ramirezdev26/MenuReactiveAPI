package co.com.menu.reactive.api.MenuReactivveAPI.usecases.interfaces;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateMenu {

    Mono<MenuDTO> update(String id, MenuDTO MenuDTO);

}
