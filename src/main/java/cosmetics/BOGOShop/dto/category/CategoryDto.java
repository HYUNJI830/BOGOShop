package cosmetics.BOGOShop.dto.category;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import cosmetics.BOGOShop.dto.item.BodyCareDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private List<SubCategoryDto> subCategories;

    public CategoryDto(Category category) {
        this.categoryId = category.getId();
        this.categoryName = category.getName();
        this.subCategories = getNullableList(category.getSubCategories().stream()
                .map(SubCategoryDto::new)
                .collect(Collectors.toList()));

    }

    private <T> List<T> getNullableList(List<T> list) {
        return list.isEmpty() ? null : list;
    }
}
