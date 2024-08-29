package cosmetics.BOGOShop.domain.item;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("H")
@Getter @Setter
public class HairItem extends Item {
    private String hairType; //헤어 타입
    private String holdStrength; //고정력


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="category_id")
//    private Category category;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "subCategory_id")
//    private SubCategory subCategory;
//


}

