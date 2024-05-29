package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.dto.order.CreateOrderRequest;
import cosmetics.BOGOShop.dto.order.CreateOrderResponse;
import cosmetics.BOGOShop.dto.order.OrderDto;
import cosmetics.BOGOShop.repository.OrderRepository;
import cosmetics.BOGOShop.repository.order.query.OrderQueryDto;
import cosmetics.BOGOShop.repository.order.query.OrderQueryRepository;
import cosmetics.BOGOShop.repository.order.query.OrderQuerydslRepository;
import cosmetics.BOGOShop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderService orderService;
    private final OrderQuerydslRepository orderQuerydslRepository;


    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();

        List<OrderDto> result = orders.stream()
                .map(o->new OrderDto(o))
                .collect(toList());
        return result;
    }

    /**
     * V3.1 엔티티를 조회해서 DTO로 변환 페이징 고려
     * - ToOne 관계만 우선 모두 페치 조인으로 최적화
     * - 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
     */

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,
                limit);
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }
    @GetMapping("/api/v4/dsl/orders")
    public List<OrderQueryDto> ordersV4Dsl(){
        return orderQuerydslRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List <OrderQueryDto> ordersV5(){
        return orderQueryRepository.findAllByDto_optimization();
    }


    //주문 생성
    //단일 주문
    @PostMapping("/api/order")
    public CreateOrderResponse saveOrder(@RequestBody @Valid CreateOrderRequest request){

       Long id =  orderService.order(request.getMemberId(),request.getItemId(),request.getCount());
        return new CreateOrderResponse(id);
    }
    //다수 주문(수정 필요)
//    @PostMapping("/api/orders")
//    public CreateOrderResponse saveOrders(@RequestBody @Valid CreateOrderRequest request){
//
//        Long id =  orderService.orders1(request.getMemberId(),request.getOrderItems());
//        return new CreateOrderResponse(id);
//    }
}
