package cosmetics.BOGOShop.domain.item;

import cosmetics.BOGOShop.domain.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString(of = {"id","name"})
public class BodyCare  {

    @Id @GeneratedValue
    @Column(name = "bodyCare_id")
    private  Long id;
    @Column(name = "bodyCare_name")
    private String name; //바디케어 카테고리 이름 : 샤워,로션, 립케어

    @OneToMany(mappedBy = "bodyCare")
    private List<BodyItem> bodyItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    public BodyCare(String name, Category category) {
        this.name = name;
        this.category = category;
        category.getBodyCares().add(this);
    }

    public void setCategory(Long categoryId) {
        if (this.category == null) {
            this.category = new Category();
        }
        category.setId(categoryId);
    }


}
