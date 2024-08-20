package cosmetics.BOGOShop.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import cosmetics.BOGOShop.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ItemDto {
    //아이템
    private Long itemId;
    private String itemName;
    private String brandName;
    private int price;
    private int stockQuantity;

    //카테고리
    private Long categoryId;
    private String categoryName;

    //서브
    private Long subCategoryId;
    private String subCategoryName;

    //생성자
    public ItemDto() {}

    public ItemDto(Item item) {
    }

    @QueryProjection
    public ItemDto(Long itemId, String itemName,String brandName,int price ,int stockQuantity ,Long categoryId, String categoryName, Long subCategoryId, String subCategoryName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.brandName = brandName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
    }

}
