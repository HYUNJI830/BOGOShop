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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="category_id") //fk
//    private Category category;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_price")
    private int price;

    @Column(name = "item_Quantity")
    private int stockQuantity; //재고수량

    @Column(name = "item_BrandName")
    private String brandName; //브랜드 이름

    public Item() {

    }
    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }


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
