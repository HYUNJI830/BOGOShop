package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.dto.order.OrderItemDto;
import cosmetics.BOGOShop.dto.order.OrderRequest;
import cosmetics.BOGOShop.dto.order.OrderResponse;
import cosmetics.BOGOShop.repository.order.query.OrderQueryDto;
import cosmetics.BOGOShop.repository.order.query.OrderQueryRepository;
import cosmetics.BOGOShop.repository.order.query.OrderQuerydslRepository;
import cosmetics.BOGOShop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

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
     * @return
     */
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
