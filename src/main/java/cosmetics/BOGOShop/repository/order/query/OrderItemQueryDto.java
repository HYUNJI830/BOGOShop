package cosmetics.BOGOShop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class OrderItemQueryDto {

    private Long orderItemId; //주문상품 번호
    private String itemName;//주문상품 명
    private int orderPrice; //주문상품 가격
    private int count; //주문상품 수량


    @QueryProjection
    public OrderItemQueryDto(Long orderItemId, String itemName, int orderPrice, int count) {
        this.orderItemId = orderItemId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

}
