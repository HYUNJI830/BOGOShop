package cosmetics.BOGOShop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Makeup extends Item {
    private String makeupCategory;
    private String brandName;
}
