package cosmetics.BOGOShop.dto.order;

import lombok.Data;

@Data
public class CreateOrderResponse {
    private Long id; //주문아이디

    public CreateOrderResponse(Long id) {
        this.id = id;
    }
}
