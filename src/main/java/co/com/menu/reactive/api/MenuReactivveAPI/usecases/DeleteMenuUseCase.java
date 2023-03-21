package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteMenuUseCase implements Function<String, Mono<String>> {

    private final IMenuRepository menuRepository;

    @Override
    public Mono<String> apply(String id) {
        return menuRepository.findById(id)
                .flatMap(menu -> menuRepository.delete(menu).thenReturn(id))
                .switchIfEmpty(Mono.error(new RuntimeException(id)));
    }
}
