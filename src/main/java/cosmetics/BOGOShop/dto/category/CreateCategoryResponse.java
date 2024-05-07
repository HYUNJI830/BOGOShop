package cosmetics.BOGOShop.dto.category;

import lombok.Data;

@Data
public class CreateCategoryResponse {
    private Long id;
    public CreateCategoryResponse(Long id){
        this.id = id;
    }
}
