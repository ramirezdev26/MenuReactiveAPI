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
class SaveMenuUseCaseTest {


    @Mock
    IMenuRepository repoMock;
    ModelMapper mapper;
    SaveMenuUseCase saveMenuUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveMenuUseCase = new SaveMenuUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("saveMenu_successfully")
    void saveMenu() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Menu newMenu = new Menu("1", "title1", 2020);
        newMenu.setId(id);
        MenuDTO newMenuDTO = new MenuDTO("1", "title1", 2020);
        newMenuDTO.setId(id);
        newMenuDTO.setItemsList(new HashSet<>());


        when(repoMock.save(newMenu)).thenReturn(Mono.just(newMenu));


        Mono<MenuDTO> response = saveMenuUseCase.save(newMenuDTO);

        StepVerifier.create(response)
                .expectNext(newMenuDTO)
                .verifyComplete();

        Mockito.verify(repoMock).save(ArgumentMatchers.any(Menu.class));
    }

    @Test
    @DisplayName("testSaveMenuError")
    public void testSaveMenuError() {
        String id = "6413683efa74e77204d881f0";
        Menu newMenu = new Menu("1", "title1", 2020);
        newMenu.setId(id);
        MenuDTO newMenuDTO = new MenuDTO("1", "title1", 2020);
        newMenuDTO.setId(id);
        newMenuDTO.setItemsList(new HashSet<>());

        when(repoMock.save(newMenu)).thenReturn(Mono.empty());

        Mono<MenuDTO> result = saveMenuUseCase.save(newMenuDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock).save(ArgumentMatchers.any(Menu.class));
    }

}