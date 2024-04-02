package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.dto.SimpleOrderDto;
import cosmetics.BOGOShop.repository.OrderRepository;
import cosmetics.BOGOShop.repository.OrderSearch;
import cosmetics.BOGOShop.repository.order.simplequery.OrderSimpleQueryDto;
import cosmetics.BOGOShop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderSimpleQueryRepository orderSimpleQueryRepository; //의존관계 주입

    @GetMapping("/api/simple-orders")
    public List<OrderSimpleQueryDto> orders() {
        return orderSimpleQueryRepository.findOrderDtos();
    }
}
