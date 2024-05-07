package cosmetics.BOGOShop.dto.order;

import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreateOrderRequest {

    private Long memberId;

    private Long itemId;
    //private List <Long> itemIds;

    private int count;
   // private List<Integer> counts;

    private List <OrderItemDto> orderItems;

    public CreateOrderRequest(Order order) {
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());
    }
}
