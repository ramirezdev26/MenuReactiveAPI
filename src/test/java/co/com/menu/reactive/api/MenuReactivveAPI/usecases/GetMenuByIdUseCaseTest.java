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

import java.util.HashSet;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMenuByIdUseCaseTest {

    @Mock
    IMenuRepository repoMock;
    ModelMapper mapper;
    GetMenuByIdUseCase getMenuByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getMenuByIdUseCase = new GetMenuByIdUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getMenuById_successfully")
    void getMenuId() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Menu newMenu = new Menu("1", "title1", 2020);
        newMenu.setId(id);
        var monoMenu = Mono.just(newMenu);
        MenuDTO newMenuDTO = new MenuDTO("1", "title1", 2020);
        newMenuDTO.setId(id);
        newMenuDTO.setItemsList(new HashSet<>());


        when(repoMock.findById(id)).thenReturn(monoMenu);


        Mono<MenuDTO> response = getMenuByIdUseCase.apply(id);

        StepVerifier.create(response)
                .expectNext(newMenuDTO)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());

    }

    @Test
    @DisplayName("testMenuNotFound")
    public void testMenuNotFound() {
        String id = "6413683efa74e77204d881f0";
        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<MenuDTO> result = getMenuByIdUseCase.apply(id);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
    }

}