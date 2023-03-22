package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.config.RabbitConfig;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.ItemDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.publisher.MenuEvent;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import co.com.menu.reactive.api.MenuReactivveAPI.usecases.interfaces.AddItem;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AddItemUseCase implements AddItem {

    private final IMenuRepository menuRepository;
    private final ModelMapper mapper;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Mono<MenuDTO> add(String menuId, ItemDTO item) {
        return this.menuRepository.findById(menuId)
                .switchIfEmpty(Mono.error(new Throwable("The menu with the id " + menuId + " was not found")))
                .flatMap(menu -> {
                    if (item.getIsAdded()) {
                        return Mono.error(new Throwable("The item is already added on a menu"));
                    } else {
                        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, new MenuEvent(item));
                        item.setIsAdded(true);
                        return menuRepository.save(menu.addItemToMenu(item));
                    }
                })
                .map(menuUpdated -> mapper.map(menuUpdated, MenuDTO.class));
    }
}
