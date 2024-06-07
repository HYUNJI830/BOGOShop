package cosmetics.BOGOShop.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ItemDto {
    //아이템
    private Long itemId;
    private String itemName;
    private int price;

    //카테고리
    private Long categoryId;
    private String categoryName;

    //서브
    private Long subCategoryId;
    private String subCategoryName;

    @QueryProjection
    public ItemDto(Long itemId, String itemName,int price ,Long categoryId, String categoryName, Long subCategoryId, String subCategoryName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
    }
}
