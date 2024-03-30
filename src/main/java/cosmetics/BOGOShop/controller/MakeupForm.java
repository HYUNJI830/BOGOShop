package cosmetics.BOGOShop.controller;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MakeupForm {

    //상품 공통 속성
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    //메이크업 속성
    private String makeupCategory;
    private String brandName;


}
