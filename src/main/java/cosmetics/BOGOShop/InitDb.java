package cosmetics.BOGOShop;

import cosmetics.BOGOShop.domain.*;
import cosmetics.BOGOShop.domain.item.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        @PersistenceContext
        private final EntityManager em;

        //꿀팁 단축키
        //따로 메서드로 분리하려는 코드를 드래그  + Ctrl + Alt + m > 메소드 분리


        public void dbInit1(){
            Member member1 = createMember("스누피", "서울","1","1111");
            em.persist(member1);

            Makeup makeup1 = createMakeup("센슈얼 누드 글로스",50000,100,"립","헤라");
            em.persist(makeup1);

            Makeup makeup2 = createMakeup("립 글로우",100000,100,"립","디올");
            em.persist(makeup2);

            //단품 주문
            OrderItem orderItem1 = OrderItem.createOrderItem(makeup1,50000,1);
            OrderItem orderItem2 = OrderItem.createOrderItem(makeup2,100000,1);

            Order order = Order.createOrder(member1, createDelivery(member1),orderItem1,orderItem2);

            em.persist(order);

        }
        public void dbInit2(){
            Member member2 = createMember("찰리", "부산","2","2222");
            em.persist(member2);

            SkinCare skinCare1 = createSkinCare("VT리들샷",32000,100,"에센스/세럼","VT");
            em.persist(skinCare1);

            SkinCare skinCare2 = createSkinCare("아토베어리어365크림",50400,100,"크림","에스트라");
            em.persist(skinCare2);

            //여러상품 주문
            List<Item> itemList = new ArrayList<>();
            itemList.add(skinCare1);
            itemList.add(skinCare2);
            List<Integer> orderPrices = Arrays.asList(skinCare1.getPrice(), skinCare2.getPrice());
            List<Integer> counts = Arrays.asList(3,4);
            List<OrderItem> orderItems = OrderItem.createOrderItems(itemList, orderPrices, counts);

            Order orders = Order.createOrders(member2,createDelivery(member2),orderItems);
            em.persist(orders);
        }

        //bodyCare
        public void dbInit3(){
            Category categoryA = new Category("바디케어");
            em.persist(categoryA);

            BodyCare bodyCareA = new BodyCare("로션",categoryA);
            BodyCare bodyCareB = new BodyCare("립케어",categoryA);
            em.persist(bodyCareA);
            em.persist(bodyCareB);

            BodyItem bodyItem11 = new BodyItem("리페어로션","일리윤",10000,bodyCareA);
            BodyItem bodyItem12 = new BodyItem("퍼퓸로션","부케가르니",7000,bodyCareA);
            BodyItem bodyItem21 = new BodyItem("딸기맛립밤","니베아",3000,bodyCareB);
            BodyItem bodyItem22 = new BodyItem("허니립마스크","에뛰드",8000,bodyCareB);
            em.persist(bodyItem11);
            em.persist(bodyItem12);
            em.persist(bodyItem21);
            em.persist(bodyItem22);
        }

        private Member createMember(String name, String city, String street, String zipcode){
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city,street,zipcode));
            return member;
        }

        private Makeup createMakeup(String name,int price,int stockQuantity,String makeupCategory,String brandName){
            Makeup makeup = new Makeup();
            makeup.setName(name);
            makeup.setPrice(price);
            makeup.setStockQuantity(stockQuantity);
            makeup.setMakeupCategory(makeupCategory);
            makeup.setBrandName(brandName);
            return makeup;
        }

        private SkinCare createSkinCare(String name,int price,int stockQuantity,String SkinCategory,String brandName){
            SkinCare skinCare = new SkinCare();
            skinCare.setName(name);
            skinCare.setPrice(price);
            skinCare.setStockQuantity(stockQuantity);
            skinCare.setSkinCategory(SkinCategory);
            skinCare.setBrandName(brandName);
            return skinCare;
        }

        private Delivery createDelivery(Member member){
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
