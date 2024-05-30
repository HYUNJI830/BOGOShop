package cosmetics.BOGOShop.dto.order;

import cosmetics.BOGOShop.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long itemId;//상품 아이디

    private String itemName;//상품 명
    private int orderPrice; //주문 가격
    private int count; //주문 수량

    public OrderItemDto(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    public OrderItemDto(OrderItem orderItem) {
        itemId =orderItem.getItem().getId();
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}
