package cosmetics.BOGOShop.dto.category;

import com.querydsl.core.annotations.QueryProjection;
import cosmetics.BOGOShop.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubCategoryDto {
    //서브카테고리
    private Long subCategoryId;
    private String subCategoryName;
    private Long categoryId;

    @QueryProjection
    public SubCategoryDto(SubCategory subCategory) {
        this.subCategoryId = subCategory.getId();
        this.subCategoryName = subCategory.getName();
        this.categoryId = subCategory.getCategory().getId();

    }
}
