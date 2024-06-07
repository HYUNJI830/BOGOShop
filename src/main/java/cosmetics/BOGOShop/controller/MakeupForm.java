package cosmetics.BOGOShop.controller;


import cosmetics.BOGOShop.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MakeupForm {

    //상품 공통 속성
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private String brandName;

    //메이크업 속성
    private Category makeupCategory;



}
