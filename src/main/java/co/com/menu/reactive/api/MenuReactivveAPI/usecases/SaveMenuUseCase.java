package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.errorHandler.NullParameterException;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import co.com.menu.reactive.api.MenuReactivveAPI.usecases.interfaces.SaveMenu;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveMenuUseCase implements SaveMenu {

    private final IMenuRepository menuRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<MenuDTO> save(MenuDTO menuDTO) {

        return this.menuRepository.save(mapper.map(menuDTO, Menu.class))
                .switchIfEmpty(Mono.error(new Throwable("Something went wrong with the request")))
                .map(menu -> mapper.map(menu, MenuDTO.class));
    }

}
