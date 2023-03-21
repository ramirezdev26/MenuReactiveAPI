package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateMenuUseCaseTest {

    @Mock
    IMenuRepository repoMock;
    ModelMapper mapper;
    UpdateMenuUseCase updateMenuUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        updateMenuUseCase = new UpdateMenuUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("updateMenu_successfully")
    void updateMenu() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Menu menu = new Menu("1", "title1", 2020);
        menu.setId(id);
        Menu menuUpdated = new Menu("2", "title2", 10);
        menuUpdated.setId(id);
        MenuDTO newMenuDTO = new MenuDTO("2", "title2", 10);
        newMenuDTO.setId(id);
        newMenuDTO.setItemsList(new HashSet<>());


        when(repoMock.findById(id)).thenReturn(Mono.just(menu));
        when(repoMock.save(menuUpdated)).thenReturn(Mono.just(menuUpdated));



        Mono<MenuDTO> response = updateMenuUseCase.update(id, newMenuDTO);

        StepVerifier.create(response)
                .expectNext(newMenuDTO)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Menu.class));
    }

    @Test
    @DisplayName("testUpdateBookError")
    public void testUpdateBookError() {
        String id = "6413683efa74e77204d881f0";
        Menu menu = new Menu("1", "title1", 2020);
        menu.setId(id);
        Menu menuUpdated = new Menu("2", "title2", 10);
        menuUpdated.setId(id);
        MenuDTO newMenuDTO = new MenuDTO("2", "title2", 10);
        newMenuDTO.setId(id);
        newMenuDTO.setItemsList(new HashSet<>());

        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<MenuDTO> result = updateMenuUseCase.update(id, newMenuDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
    }

}