package co.com.menu.reactive.api.MenuReactivveAPI.domain.dto;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Item;
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

    private Set<Item> itemsList = new HashSet<>();

    public MenuDTO(String date, String version, Integer promo) {
        this.date = date;
        this.version = version;
        this.promo = promo;
    }

}
