package co.com.menu.reactive.api.MenuReactivveAPI.domain.collection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "menu")
public class Menu {
    @Id
    private String id;

    @NotBlank(message="Empty field error")
    @NotNull(message ="date is required")
    private String date;

    @NotBlank(message="Empty field error")
    @NotNull(message ="version is required")
    private String version;

    private Integer promo;

    private Set<ItemDTO> itemsList = new HashSet<>();


    public Menu(String date, String version, Integer promo) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.date = date;
        this.version = version;
        this.promo = promo;
        this.itemsList = new HashSet<>();
    }

    public Menu addItemToMenu(ItemDTO itemdto){
        this.itemsList.add(itemdto);
        return this;
    }

}
