package co.com.menu.reactive.api.MenuReactivveAPI.domain.dto;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.ItemDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    private String id;

    private String date;

    private String version;

    private Integer promo;

    private Set<ItemDTO> itemsList = new HashSet<>();

    public MenuDTO(String date, String version, Integer promo) {
        this.date = date;
        this.version = version;
        this.promo = promo;
    }

    public MenuDTO addItemToMenu(ItemDTO itemdto){
        this.itemsList.add(itemdto);
        return this;
    }
}
