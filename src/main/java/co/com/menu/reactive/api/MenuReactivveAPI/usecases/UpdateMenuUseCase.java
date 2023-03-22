package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.errorHandler.NullParameterException;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import co.com.menu.reactive.api.MenuReactivveAPI.usecases.interfaces.UpdateMenu;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateMenuUseCase implements UpdateMenu {

    private final IMenuRepository menuRepository;

    private final ModelMapper mapper;



    @Override
    public Mono<MenuDTO> update(String id, MenuDTO menuDTO) {

        return this.menuRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("The menu with the id " + id + " was not found")))
                .flatMap(menu -> {
                    menuDTO.setId(menu.getId());
                    return menuRepository.save(mapper.map(menuDTO, Menu.class));
                })
                .map(menu -> mapper.map(menu, MenuDTO.class));
    }
}
