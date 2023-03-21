package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetMenuByIdUseCase implements Function<String, Mono<MenuDTO>> {
    private final IMenuRepository menuRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<MenuDTO> apply(String id) {
        return this.menuRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable()))
                .map(menu-> mapper.map(menu, MenuDTO.class));
    }
}
