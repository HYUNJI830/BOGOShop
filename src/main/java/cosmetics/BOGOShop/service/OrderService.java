package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.Delivery;
import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.domain.OrderItem;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.dto.order.OrderItemDto;
import cosmetics.BOGOShop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        //주문 생성
        Order order = Order.createOrder(member,delivery,orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public Long orders(Long memberId, List<Long> itemIds,List<Integer> counts){

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        List<Item> items = itemRepository.findAllById(itemIds);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        List<Integer> prices = new ArrayList<>();
        for (Item item : items) {
            prices.add(item.getPrice());
        }

        //주문상품 생성
        List<OrderItem> orderItems =  OrderItem.createOrderItems(items,prices,counts);

        //주문 생성
        Order order = Order.createOrders(member,delivery,orderItems);

        //주문 저장
        orderRepository.save(order);

        return order.getId();

    }

    @Transactional
    public Long ordersNew(Long memberId, List<OrderItemDto> orderItemDtos){

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemDto orderItemDto : orderItemDtos){
            Item item = itemRepository.findOne(orderItemDto.getItemId());
            OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),orderItemDto.getCount());
            orderItems.add(orderItem);
        }

        //주문 생성
        Order order = Order.createOrders(member,delivery,orderItems);

        //주문 저장
        orderRepository.save(order);

        return order.getId();

    }


    /**
     * 주문 취소
     */
    @Transactional
    public void  cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
    }
    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByCondition(orderSearch);
    }

    /**
     * 주문 전체 검색
     */
    public List<Order> findOrdersAll(){
        return orderRepository.findAll();
    }


}
