package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.dto.order.OrderDto;
import cosmetics.BOGOShop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o->new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

}
