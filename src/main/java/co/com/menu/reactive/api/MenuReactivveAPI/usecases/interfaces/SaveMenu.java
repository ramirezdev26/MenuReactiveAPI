package co.com.menu.reactive.api.MenuReactivveAPI.usecases.interfaces;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SaveMenu {
    Mono<MenuDTO> save(MenuDTO menuDTO);
}
