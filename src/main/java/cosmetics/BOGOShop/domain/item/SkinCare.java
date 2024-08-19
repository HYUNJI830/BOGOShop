package cosmetics.BOGOShop.domain.item;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("S")
@Getter @Setter
public class SkinCare extends Item {
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="category_id")
//    private Category category;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "subCategory_id")
//    private SubCategory subCategory;
}
