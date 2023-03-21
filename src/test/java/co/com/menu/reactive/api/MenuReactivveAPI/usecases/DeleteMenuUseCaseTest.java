package co.com.menu.reactive.api.MenuReactivveAPI.usecases;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import co.com.menu.reactive.api.MenuReactivveAPI.repository.IMenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuUseCaseTest {

    @Mock
    IMenuRepository repoMock;

    @InjectMocks
    DeleteMenuUseCase deleteMenuUseCase;

    @Test
    @DisplayName("deleteMenuTest_Success")
    void deleteMenu() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Menu newMenu = new Menu("1", "title1", 2020);
        newMenu.setId(id);
        var monoMenu = Mono.just(newMenu);

        when(repoMock.findById(id)).thenReturn(monoMenu);
        when(repoMock.delete(newMenu)).thenReturn(Mono.empty());


        Mono<String> response = deleteMenuUseCase.apply(id);

        StepVerifier.create(response)
                .expectNext("6413683efa74e77204d881f0")
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).delete(ArgumentMatchers.any(Menu.class));

    }

    @Test
    @DisplayName("testDeleteMenuNotFound")
    public void testDeleteMenuNotFound() {
        String id = "6413683efa74e77204d881f0";
        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<String> result = deleteMenuUseCase.apply(id);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock, never()).delete(ArgumentMatchers.any(Menu.class));
    }

}