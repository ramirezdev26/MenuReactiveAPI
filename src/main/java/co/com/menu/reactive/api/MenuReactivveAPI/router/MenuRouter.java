package co.com.menu.reactive.api.MenuReactivveAPI.router;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.ItemDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.dto.MenuDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MenuRouter {

    private final WebClient itemAPI;

    public MenuRouter() { this.itemAPI = WebClient.create("http://localhost:8080/items"); }

    @Bean
    public RouterFunction<ServerResponse> getAllMenu(GetAllMenuUseCase getAllMenuUseCase){
        return route(GET("/menu"),
                request -> getAllMenuUseCase.get()
                .collectList()
                .flatMap(menuDTOS -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllMenuUseCase.get(), MenuDTO.class)))
                .onErrorResume(throwable -> ServerResponse.noContent().build()));

    }

    @Bean
    public RouterFunction<ServerResponse> getMenuById(GetMenuByIdUseCase getMenuByIdUseCase){
        return route(GET("/menu/{id}"),
                request -> getMenuByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(menuDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(menuDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveMenu(SaveMenuUseCase saveMenuUseCase){
            return route(POST("/menu").and(accept(MediaType.APPLICATION_JSON)),
                    request -> request.bodyToMono(MenuDTO.class)
                            .flatMap(menuDTO -> saveMenuUseCase.save(menuDTO)
                                    .flatMap(result -> ServerResponse.status(201)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(result))

                                    .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Invalid request data: " + throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateMenu(UpdateMenuUseCase updateMenuUseCase){
        return route(PUT("/menu/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(MenuDTO.class)
                        .switchIfEmpty(Mono.error(new Throwable()))
                        .flatMap(menuDTO -> updateMenuUseCase.update(request.pathVariable("id"), menuDTO))
                        .flatMap(result -> ServerResponse.status(200)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Invalid request data: " + throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteMenu(DeleteMenuUseCase deleteMenuUseCase){
        return route(DELETE("/menu/{id}"),
                request -> deleteMenuUseCase.apply(request.pathVariable("id"))
                        .flatMap(string -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("The menu with the fabulous id: " + string + " was deleted"))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> addItem(AddItemUseCase addItemUseCase) {
        return route(PATCH("/menu/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request ->
                        request.bodyToMono(ItemDTO.class)
                                .flatMap(itemDTO -> itemAPI.get().uri("/" + itemDTO.getId())
                                        .retrieve()
                                        .bodyToMono(ItemDTO.class)
                                        .flatMap(item ->
                                                addItemUseCase.add(request.pathVariable("id"), item)
                                                        .switchIfEmpty(Mono.error(new Throwable("Didn't find menu'")))
                                                        .flatMap(result -> ServerResponse.status(200)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .bodyValue(result))
                                                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build()))));
    }


}
