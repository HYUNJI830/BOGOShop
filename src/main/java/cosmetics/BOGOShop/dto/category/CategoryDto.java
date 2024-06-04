package cosmetics.BOGOShop.dto.category;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.dto.item.BodyCareDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long Id;
    private String name;
    private List<BodyCareDto> bodyCare;
    //private List<CategoryChildDto> child;

    public CategoryDto(Category category) {
        Id = category.getId();
        name = category.getName();
        bodyCare = category.getBodyCares().stream()
                .map(bodyCare -> new BodyCareDto(bodyCare))
                .collect(Collectors.toList());
//
//        child = category.getChild().stream()
//                .map(categoryChild -> new CategoryChildDto(categoryChild))
//                .collect(Collectors.toList());
//    }

    }
}
