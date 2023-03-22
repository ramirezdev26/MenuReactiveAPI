package co.com.menu.reactive.api.MenuReactivveAPI.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private String id;
    private String category;
    private String description;
    private String name;
    private Integer price;
    private Boolean isAdded = false;


}
