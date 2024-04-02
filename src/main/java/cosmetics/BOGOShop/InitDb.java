package cosmetics.BOGOShop;

import cosmetics.BOGOShop.domain.*;
import cosmetics.BOGOShop.domain.item.Makeup;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){
            Member member = createMember("userA", "서울","1","1111");
            em.persist(member);

            Makeup makeup1 = createMakeup("헤라",50000,100);
            em.persist(makeup1);

            Makeup makeup2 = createMakeup("디올",100000,100);
            em.persist(makeup2);

            OrderItem orderItem1 = OrderItem.createOrderItem(makeup1,50000,1);
            OrderItem orderItem2 = OrderItem.createOrderItem(makeup2,100000,1);
            Order order = Order.createOrder(member, createDelivery(member),orderItem1,orderItem2);
            em.persist(order);
        }


        private Member createMember(String name, String city, String street, String zipcode){
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city,street,zipcode));
            return member;
        }

        private Makeup createMakeup(String name,int price,int stockQuantity){
            Makeup makeup = new Makeup();
            makeup.setName(name);
            makeup.setPrice(price);
            makeup.setStockQuantity(stockQuantity);
            return makeup;
        }

        private Delivery createDelivery(Member member){
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
