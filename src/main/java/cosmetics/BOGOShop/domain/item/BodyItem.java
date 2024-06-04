package cosmetics.BOGOShop.domain.item;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@DiscriminatorValue("B")
@NoArgsConstructor
//@ToString(of={"id","name","brandName","price"})
public class BodyItem {
    @Id
    @GeneratedValue
    @Column(name = "BodyItem_id")
    private Long id;

    @Column(name = "BodyItem_name")
    private String name; //바디제품 이름
    @Column(name = "BodyItem_brandName")
    private String brandName; //바디제품 브랜드 이름
    @Column(name = "BodyItem_price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bodyCare_id")
    private BodyCare bodyCare;

//    public BodyItem(String name) {
//        //this.name = name;
//        this(name,"",0,null);
//    }
//
//    public BodyItem(String name, String brandName, int price) {
//        //this.name = name;
//        //this.brandName = brandName;
//        //this.price = price;
//        this(name,brandName,price,null);
//    }
//

    public BodyItem(String name, String brandName, int price, BodyCare bodyCare) {
        this.name = name;
        this.brandName = brandName;
        this.price = price;
        if(bodyCare !=null){
            changeBodyCare(bodyCare);
        }
    }


    public void changeBodyCare(BodyCare bodyCare){
        this.bodyCare = bodyCare;
        bodyCare.getBodyItems().add(this);
    }
}
