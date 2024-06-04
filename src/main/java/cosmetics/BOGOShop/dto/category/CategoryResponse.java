package cosmetics.BOGOShop.dto.category;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    public CategoryResponse(Long id){
        this.id = id;
    }
}
