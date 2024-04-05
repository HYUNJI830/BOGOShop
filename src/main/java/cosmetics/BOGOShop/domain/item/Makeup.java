package cosmetics.BOGOShop.domain.item;

import cosmetics.BOGOShop.domain.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Makeup extends Item {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    private String makeupCategory;
    private String brandName;
}
