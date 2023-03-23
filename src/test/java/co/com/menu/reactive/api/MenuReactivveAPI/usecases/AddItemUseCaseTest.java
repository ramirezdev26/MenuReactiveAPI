package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.config.RabbitConfig;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.ItemDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.publisher.MenuEvent;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddItemUseCaseTest {


    @Mock
    IMenuRepository repoMock;

    ModelMapper mapper;
    @Mock
    RabbitTemplate rabbitTemplate;


    AddItemUseCase addItemUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        addItemUseCase = new AddItemUseCase(repoMock, mapper, rabbitTemplate);
    }

    @Test
    @DisplayName("addItem_successfully")
    void addItem() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        String idItem = "6413683efa74e77204d88";
        Menu menu = new Menu("1", "title1", 2020);
        menu.setId(id);
        ItemDTO item = new ItemDTO( idItem,
        "category",
        "description",
        "name",
        20, false);
        ItemDTO itemTrue = new ItemDTO( idItem,
                "category",
                "description",
                "name",
                20, false);
        itemTrue.setIsAdded(true);
        Menu menuUpdated = new Menu("1", "title1", 2020);
        menuUpdated.setId(id);
        menuUpdated.addItemToMenu(item);
        MenuDTO menuUpdatedDTO = new MenuDTO("1", "title1", 2020);
        menuUpdatedDTO.setId(id);
        menuUpdatedDTO.addItemToMenu(itemTrue);


        when(repoMock.findById(id)).thenReturn(Mono.just(menu));
        when(repoMock.save(any(Menu.class))).thenReturn(Mono.just(menuUpdated));

        StepVerifier.create(addItemUseCase.add(id, item))
                .expectNext(menuUpdatedDTO)
                .verifyComplete();

        verify(repoMock).findById(id);
        verify(repoMock).save(any(Menu.class));
        verify(rabbitTemplate).convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, new MenuEvent(item));
    }

    @Test
    @DisplayName("testAddItemError")
    public void testAddItemError() {
        String id = "6413683efa74e77204d881f0";
        String idItem = "6413683efa74e77204d88";
        ItemDTO item = new ItemDTO( idItem,
                "category",
                "description",
                "name",
                20, false);

        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<MenuDTO> result = addItemUseCase.add(id, item);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
    }

}