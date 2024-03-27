package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.Delivery;
import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.domain.OrderItem;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.repository.ItemRepository;
import cosmetics.BOGOShop.repository.MemberRepository;
import cosmetics.BOGOShop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Member member = memberRepository.findOne(memberId);
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

    //취소
    //검색
}
