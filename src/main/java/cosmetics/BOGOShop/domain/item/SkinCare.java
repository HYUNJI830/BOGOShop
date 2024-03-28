package cosmetics.BOGOShop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("S")
@Getter @Setter
public class SkinCare extends Item {
    private String SkinCategory;
    private String brandName;
}
