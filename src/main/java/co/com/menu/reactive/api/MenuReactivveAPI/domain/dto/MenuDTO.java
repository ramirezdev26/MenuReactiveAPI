package co.com.menu.reactive.api.MenuReactivveAPI.domain.dto;

import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.ItemDTO;
import co.com.menu.reactive.api.MenuReactivveAPI.domain.collection.Menu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message="Empty field error")
    @NotNull(message ="date is required")
    private String date;

    @NotBlank(message="Empty field error")
    @NotNull(message ="version is required")
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
