package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllMenuUseCaseTest {


    @Mock
    IMenuRepository repoMock;

    ModelMapper modelMapper;

    GetAllMenuUseCase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new GetAllMenuUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getAllMenu_Success")
    void getAllMenu() {
        //Build the scenario you need
        var fluxMenu = Flux.just(new Menu("1", "title1", 2020), new Menu("2", "title2", 2022));

        Mockito.when(repoMock.findAll()).thenReturn(fluxMenu);

        var response = service.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repoMock).findAll();
    }

}