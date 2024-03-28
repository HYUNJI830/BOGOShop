package cosmetics.BOGOShop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("H")
@Getter @Setter
public class HairCare extends Item {

    private String hairCategory;
    private String brandName;

}
