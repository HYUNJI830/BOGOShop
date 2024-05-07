package cosmetics.BOGOShop.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cosmetics.BOGOShop.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long ParentId;
    private String name;
    private List<CategoryChildDto> child;

    public CategoryDto(Category category){
        ParentId = category.getId();
        name = category.getName();
        child = category.getChild().stream()
                .map(categoryChild -> new CategoryChildDto(categoryChild))
                .collect(Collectors.toList());
    }


}
