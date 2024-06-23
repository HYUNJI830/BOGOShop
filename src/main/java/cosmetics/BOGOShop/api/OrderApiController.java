package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.dto.order.OrderDto;
import cosmetics.BOGOShop.dto.order.OrderRequest;
import cosmetics.BOGOShop.dto.order.OrderResponse;
import cosmetics.BOGOShop.repository.OrderRepository;
import cosmetics.BOGOShop.repository.order.optimization.OrderQueryDto;
import cosmetics.BOGOShop.repository.order.optimization.OrderQueryRepository;
import cosmetics.BOGOShop.repository.order.optimization.OrderQuerydslRepository;
import cosmetics.BOGOShop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderService orderService;
    private final OrderQuerydslRepository orderQuerydslRepository;

    /**
     * 주문생성
     */
    @PostMapping("/api/order")
    public OrderResponse saveOrder(@RequestBody @Valid OrderRequest request){
        Long id =  orderService.order(request.getMemberId(),request.getItemId(),request.getCount());
        return new OrderResponse(id);
    }
    //다수주문
    @PostMapping("/api/orders")
    public List<OrderResponse> saveOrders(@RequestBody @Valid List<OrderRequest> requests){
        List<OrderResponse> responses = new ArrayList<>();

        for (OrderRequest request : requests) {
            Long id =  orderService.ordersNew(request.getMemberId(),request.getOrderItems());
            responses.add(new OrderResponse(id));
        }

        return responses;
    }


    /**
     * 주문 조회
     */
    @GetMapping("/api/pageOld/orders")
    public List<OrderDto> ordersPageOld(){
        List<Order> orders = orderRepository.findAllWithItemOld();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/page/orders")
    public List<OrderDto> ordersPage() {
        List<Order> orders = orderRepository.findAllWithItem();
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

    @GetMapping("/api/v5/dsl/orders")
    public List <OrderQueryDto> ordersV5Dsl(){
        return orderQuerydslRepository.findAllByDto_optimization();
    }

    /**
     * 주문삭제
     */
    @PostMapping("/api/dsl/orders/cancel")
    public void cancelOrder(@RequestBody @Valid OrderRequest request){
        orderService.cancelOrder(request.getOrderId());
    }

}
