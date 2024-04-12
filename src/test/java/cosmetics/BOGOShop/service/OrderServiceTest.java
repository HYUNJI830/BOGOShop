package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.*;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.Makeup;
import cosmetics.BOGOShop.exception.NotEnoughStockException;
import cosmetics.BOGOShop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();
        Item item = createMakeup("헤라 선크림",50000,10);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER,getOrder.getStatus());
        assertEquals("주문한 상품 수량이 정확해야 한다.", 1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 50000*2,getOrder.getTotalPrice());
        assertEquals("주문 수량 만큼 재고가 감소해야 한다.", 8,item.getStockQuantity());
    }

    @Test
    public void 다수_상품주문() throws Exception {
        //given
        Member member = createMember();
        Item item1 = createMakeup("페리페라 틴트", 1000, 10);
        Item item2 = createMakeup("헤라 선크림", 100, 10);
        List<Long> itemIds = Arrays.asList(item1.getId(), item2.getId());
        List<Integer> orderCounts = Arrays.asList(2, 3);

        //when
        Long orderId = orderService.orders(member.getId(),itemIds ,orderCounts);
        Order getorder = orderRepository.findOne(orderId);
        List<OrderItem> orderItems = getorder.getOrderItems();
        System.out.println("주문항목들");
        for(OrderItem orderItem:orderItems){
            System.out.println("아이디 = " + orderItem.getItem().getId());
            System.out.println("이름" + orderItem.getItem().getName());
            System.out.println("가격 " + orderItem.getItem().getPrice());
        }

        //then
        Order getOrder = orderRepository.findOne(orderId);

        //상품에 대한 주문 검증
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("상품 주문한 상품 종류가 정확해야 한다.", 2, getOrder.getOrderItems().size());
        assertEquals("상품 주문 가격은 가격 * 수량이다.", 1000*2+100*3, getOrder.getTotalPrice());
        assertEquals("상품 주문 수량 만큼 재고가 감소해야 한다.", 8, item1.getStockQuantity());
        assertEquals("상품 주문 수량 만큼 재고가 감소해야 한다.", 7, item2.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createMakeup("헤라 선크림",50000,10);

        int orderCount = 11;

        //when
        orderService.order(member.getId(),item.getId(),orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Makeup item = createMakeup("헤라 선크림",50000,10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.",OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.",10,item.getStockQuantity());
    }

    private Member createMember(){
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","관악구","123-123"));
        em.persist(member);
        return member;
    }

    private Makeup createMakeup(String name, int price, int stockQuantity){
        Makeup makeup = new Makeup();
        makeup.setName(name);
        makeup.setPrice(price);
        makeup.setStockQuantity(stockQuantity);
        em.persist(makeup);
        return makeup;
    }

}