package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.dto.order.OrderDto;
import cosmetics.BOGOShop.dto.order.OrderRequest;
import cosmetics.BOGOShop.dto.order.OrderResponse;
import cosmetics.BOGOShop.repository.OrderRepository;
import cosmetics.BOGOShop.repository.order.optimization.OrderQueryDto;
import cosmetics.BOGOShop.repository.order.optimization.OrderQueryRepository;
import cosmetics.BOGOShop.repository.order.optimization.OrderQuerydslRepository;
import cosmetics.BOGOShop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문 API", description = "주문 컨트롤러에 대한 설명입니다.")
public class OrderApiController {

    private final OrderQueryRepository orderQueryRepository;
    private final OrderService orderService;
    private final OrderQuerydslRepository orderQuerydslRepository;

    @Operation(summary = "단일 주문 생성", description ="단일 주문을 생성합니다.")
    @Parameter(name = "memberId", description = "주문자 아이디")
    @Parameter(name = "itemId", description = "주문 상품 아이디")
    @Parameter(name = "count", description = "주문 상품 수량")
    @PostMapping("/api/order")
    public OrderResponse saveOrder(@RequestBody @Valid OrderRequest request){
        Long id =  orderService.order(request.getMemberId(),request.getItemId(),request.getCount());
        return new OrderResponse(id);
    }
    @Operation(summary = "다수 주문 생성", description ="다수 주문을 생성합니다.")
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
    @Operation(summary = "주문 조회", description ="JPA 사용해서 주문을 조회합니다.")
    @GetMapping("/api/orders/v4")
    public List<OrderQueryDto> ordersV4(@RequestParam(value = "offset",defaultValue = "0") int offset,@RequestParam(value = "limit",defaultValue = "1") int limit){
        return orderQueryRepository.findOrderQueryDtos(offset,limit);
    }
    @Operation(summary = "주문 조회", description ="QueryDSL 사용해서 주문을 조회합니다.")
    @GetMapping("/api/orders/v4/dsl")
    public List<OrderQueryDto> ordersV4Dsl(@RequestParam(value = "offset",defaultValue = "0") int offset,@RequestParam(value = "limit",defaultValue = "1") int limit){
        return orderQuerydslRepository.findOrderQueryDtos(offset,limit);
    }

    @Operation(summary = "주문 조회", description ="JPA 사용해서 주문을 조회합니다.")
    @GetMapping("/api/orders/v5")
    public List <OrderQueryDto> ordersV5(@RequestParam(value = "offset",defaultValue = "0") int offset,@RequestParam(value = "limit",defaultValue = "1") int limit){
        return orderQueryRepository.findAllByDto_optimization(offset,limit);
    }

    @Operation(summary = "주문 조회", description ="QueryDSL 사용해서 주문을 조회합니다.")
    @GetMapping("/api/orders/v5/dsl")
    public List <OrderQueryDto> ordersV5Dsl(@RequestParam(value = "offset",defaultValue = "0") int offset,@RequestParam(value = "limit",defaultValue = "1") int limit){
        return orderQuerydslRepository.findAllByDto_optimization(offset,limit);
    }

    @Operation(summary = "주문 취소", description = "파라미터로 주문 ID를 전송하여 주문ID에 해당하는 주문을 삭제합니다.")
    @Parameter(name = "orderId", description = "삭제하려는 주문 아이디")
    @PostMapping("/api/orders/cancel")
    public void cancelOrder(@RequestParam(required = true) Long orderId){
        orderService.cancelOrder(orderId);
    }

}
