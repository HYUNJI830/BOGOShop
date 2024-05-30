package cosmetics.BOGOShop.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private Long orderId; //주문아이디

    public OrderResponse(Long id) {
        this.orderId = id;
    }
}
