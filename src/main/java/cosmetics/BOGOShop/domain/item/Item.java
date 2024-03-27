package cosmetics.BOGOShop.domain.item;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public  abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity; //재고수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스로직==//

    /**
     *
     * Stock 증감
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    public  void removeStock(int quantity){
        int restStock = this.stockQuantity -quantity;
        if(restStock <0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


}
