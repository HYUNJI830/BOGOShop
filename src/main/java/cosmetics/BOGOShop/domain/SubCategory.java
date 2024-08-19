package cosmetics.BOGOShop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cosmetics.BOGOShop.domain.item.Item;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class SubCategory {
    @Id
    @GeneratedValue
    @Column(name = "sub_category_id")
    private Long id;

    @Column(name = "sub_category_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

//    @JsonIgnore
//    @OneToOne(mappedBy = "subCategory", fetch = FetchType.LAZY)
//    private Item item;
    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    public SubCategory() {
    }

    public SubCategory(String name,Category category) {
        this.name = name;
        this.category = category;
        category.getSubCategories().add(this);
    }
//    public void setCategory(Category category) {
//        this.category = category;
//        category.getSubCategories().add(this);
//    }
    public void setCategoryID(Long categoryId) {
        if (this.category == null) {
            this.category = new Category();
        }
        category.setId(categoryId);
    }

}
