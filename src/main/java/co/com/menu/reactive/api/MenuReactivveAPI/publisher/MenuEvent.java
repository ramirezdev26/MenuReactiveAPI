package co.com.menu.reactive.api.MenuReactivveAPI.publisher;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuEvent {

    private ItemDTO itemDTO;
    private final String eventType = "Append.Item";

}
