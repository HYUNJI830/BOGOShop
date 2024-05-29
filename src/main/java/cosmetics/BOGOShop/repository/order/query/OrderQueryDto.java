package cosmetics.BOGOShop.repository.order.query;

import com.querydsl.core.annotations.QueryProjection;
import cosmetics.BOGOShop.domain.Address;
import cosmetics.BOGOShop.domain.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {

    private Long orderId; //주문아이디
    private String memberName; //주문자 이름
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus; //주문상태
    private Address address; //주소
    private List<OrderItemQueryDto> orderItems;

    @QueryProjection
    public OrderQueryDto(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
