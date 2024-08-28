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
    private String skinType; //피부 타입
    //private String activeIngredients; // 활성 성분

}
