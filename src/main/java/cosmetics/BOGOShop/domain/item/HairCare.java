package cosmetics.BOGOShop.domain.item;

import cosmetics.BOGOShop.domain.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("H")
@Getter @Setter
public class HairCare extends Item {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    private String hairCategory;
    private String brandName;

}
