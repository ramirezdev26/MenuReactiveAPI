package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllMenuUseCase implements Supplier<Flux<MenuDTO>> {
    private final IMenuRepository menuRepository;

    private final ModelMapper mapper;

    @Override
    public Flux<MenuDTO> get() {
        return this.menuRepository
                .findAll()
                .switchIfEmpty(Flux.error(new Throwable()))
                .map(menu -> mapper.map(menu, MenuDTO.class));
    }
}
