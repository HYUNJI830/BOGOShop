package cosmetics.BOGOShop;

import cosmetics.BOGOShop.domain.*;
import cosmetics.BOGOShop.domain.item.*;
import cosmetics.BOGOShop.domain.item.pattern.ItemStrategy;
import cosmetics.BOGOShop.dto.item.ItemDto;
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
        initService.dbInit4();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        @PersistenceContext
        private final EntityManager em;
        private final ItemStrategy itemStrategy;

        public void dbInit4() {
            int batchSize = 1000;
            int totalOrders = 100;

            Member member4 = createMember("userD","1234","페퍼민트", "인천","4","4");
            em.persist(member4);
            // 3. ItemDto 생성
            ItemDto makeupDto = ItemDto.builder()
                    .itemName("벨벳립틴트")
                    .price(12000)
                    .stockQuantity(1000000)
                    .brandName("3CE")
                    .categoryId(1L)
                    .subCategoryId(1L)
                    .build();

            // 4. ItemStrategy를 통해 Item 생성 및 저장
            Item makeup4 = itemStrategy.createItem(makeupDto);
            em.persist(makeup4);

            for (int i = 0; i < totalOrders; i++) {

                OrderItem orderItem = OrderItem.createOrderItem(makeup4, makeup4.getPrice(), 1);

                Order order = Order.createOrder(member4,createDelivery(member4), orderItem);
                em.persist(order);

                if (i > 0 && i % batchSize == 0) {
                    em.flush();
                    em.clear();
                }
            }

            em.flush();
            em.clear();
        }


        public void dbInit1(){
            Member member1 = createMember("userA","1234","스누피", "서울","1","1111");

            em.persist(member1);

            Category categoryM = new Category("메이크업");
            em.persist(categoryM);

            SubCategory subCategoryM = new SubCategory("립",categoryM);
            em.persist(subCategoryM);

            // 3. ItemDto 생성
            ItemDto makeupDto = ItemDto.builder()
                    .itemName("센슈얼 누드 글로스")
                    .price(50000)
                    .stockQuantity(100)
                    .brandName("디올")
                    .categoryId(categoryM.getId())
                    .subCategoryId(subCategoryM.getId())
                    .build();

            // 4. ItemStrategy를 통해 Item 생성 및 저장
            Item makeup1 = itemStrategy.createItem(makeupDto);
            em.persist(makeup1);

            //단품 주문
            OrderItem orderItem1 = OrderItem.createOrderItem(makeup1,50000,1);

            Order order = Order.createOrder(member1, createDelivery(member1),orderItem1);

            em.persist(order);

        }
        public void dbInit2(){
            Member member2 = createMember("userB","5678","찰리", "부산","2","2222");

            em.persist(member2);


            Category categoryS = new Category("스킨케어");
            em.persist(categoryS);

            SubCategory subCategoryS1 = new SubCategory("에센스/세럼",categoryS);
            SubCategory subCategoryS2 = new SubCategory("크림",categoryS);
            em.persist(subCategoryS1);
            em.persist(subCategoryS2);



            ItemDto skinDto1 = ItemDto.builder()
                    .itemName("VT리들샷")
                    .price(32000)
                    .stockQuantity(100)
                    .brandName("VT")
                    .categoryId(categoryS.getId())
                    .subCategoryId(subCategoryS1.getId())
                    .build();
            Item skinCare1 = itemStrategy.createItem(skinDto1);
            em.persist(skinCare1);


            ItemDto skinDto2 = ItemDto.builder()
                    .itemName("아토베어리어365크림")
                    .price(50000)
                    .stockQuantity(100)
                    .brandName("에스트라")
                    .categoryId(categoryS.getId())
                    .subCategoryId(subCategoryS2.getId())
                    .build();
            Item skinCare2 = itemStrategy.createItem(skinDto2);
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

        //hairCare
        public void dbInit3(){
            Member member3 = createMember("userC","0000","샐리", "청주","3","333");
            em.persist(member3);

            Category categoryH = new Category("헤어케어");
            em.persist(categoryH);

            SubCategory subCategoryH = new SubCategory("염색",categoryH);
            em.persist(subCategoryH);

            ItemDto hairDto = ItemDto.builder()
                    .itemName("퍼펙트세럼")
                    .price(8000)
                    .stockQuantity(1000)
                    .brandName("미장센")
                    .categoryId(categoryH.getId())
                    .subCategoryId(subCategoryH.getId())
                    .build();
            Item hairItem = itemStrategy.createItem(hairDto);
            em.persist(hairItem);

            OrderItem orderHairItem = OrderItem.createOrderItem(hairItem,8000,10);

            Order order = Order.createOrder(member3, createDelivery(member3),orderHairItem);
            em.persist(order);
        }


        private Member createMember(String userId,String password,String name, String city, String street, String zipcode){
            Member member = new Member();
            member.setUserId(userId);
            member.setPassword(password);
            member.setStatus(MemberStatus.MEMBER);
            member.setName(name);
            member.setAddress(new Address(city,street,zipcode));
            return member;
        }


        private Delivery createDelivery(Member member){
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
