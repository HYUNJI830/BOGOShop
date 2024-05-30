package cosmetics.BOGOShop.dto.order;

import cosmetics.BOGOShop.domain.Order;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderRequest {
    //삭제할때 필요
    private Long orderId;

    //단일주문 필요
    private Long memberId;

    private Long itemId;

    private int count;


    private List <OrderItemDto> orderItems;

    public OrderRequest(Long memberId, List<OrderItemDto> orderItems) {
        this.memberId = memberId;
        this.orderItems = orderItems;
    }

    //
//
//
//    public OrderRequest(Order order) {
//        orderItems = order.getOrderItems().stream()
//                .map(orderItem -> new OrderItemDto(orderItem))
//                .collect(Collectors.toList());
//    }
}
