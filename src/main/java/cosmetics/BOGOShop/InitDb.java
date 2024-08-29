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
        //initService.dbInit();
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
        private final ItemStrategy itemStrategy;

//        public void dbInit() {
//            int batchSize = 1000;
//            List<Order> orders = new ArrayList<>();
//
//            for (int i = 0; i < 10000; i++) {
//                Member member = createMember(i);
//                Delivery delivery = createDelivery();
//                OrderItem orderItem1 = createOrderItem("Item" + i + "_1");
//                OrderItem orderItem2 = createOrderItem("Item" + i + "_2");
//
//                Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
//                orders.add(order);
//
//                if (i > 0 && i % batchSize == 0) {
//                    em.flush();
//                    em.clear();
//                }
//            }
//
//            for (Order order : orders) {
//                em.persist(order);
//            }
//            em.flush();
//            em.clear();
//        }
//
//        private Member createMember(int i) {
//            Member member = new Member();
//            member.setName("Member" + i);
//            member.setAddress(new Address("City" + i, "Street" + i, "Zipcode" + i));
//            em.persist(member);
//            return member;
//        }
//
//        private Delivery createDelivery() {
//            Delivery delivery = new Delivery();
//            delivery.setAddress(new Address("City", "Street", "Zipcode"));
//            delivery.setStatus(DeliveryStatus.READY);
//            em.persist(delivery);
//            return delivery;
//        }
//
//        private OrderItem createOrderItem(String itemName) {
//            // 1. Item 생성 및 데이터 설정
//            Item item = new Makeup();
//            item.setName(itemName);
//            item.setPrice(10000);
//            item.setStockQuantity(100);
//
//            // 2. Item 엔티티를 영속화 (DB에 저장)
//            em.persist(item);
//
//            // 3. OrderItem 생성
//            return OrderItem.createOrderItem(item, item.getPrice(), 2);
//        }


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


            SkinCare skinCare1 = createSkinCare("VT리들샷",32000,100,"VT",subCategoryS1.getCategory(),subCategoryS1);
            em.persist(skinCare1);

            SkinCare skinCare2 = createSkinCare("아토베어리어365크림",50400,100,"에스트라",subCategoryS2.getCategory(),subCategoryS2);
            em.persist(skinCare2);
            SkinCare skinCare3 = createSkinCare("알로에수딩젤",100000,10,"김정문 알로에",subCategoryS1.getCategory(),subCategoryS1);
            em.persist(skinCare3);

            //여러상품 주문
            List<Item> itemList = new ArrayList<>();
            itemList.add(skinCare1);
            itemList.add(skinCare2);
            itemList.add(skinCare3);

            List<Integer> orderPrices = Arrays.asList(skinCare1.getPrice(), skinCare2.getPrice(),skinCare3.getPrice());
            List<Integer> counts = Arrays.asList(3,4,5);
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

            Item hairItem = createHairItem("헬로버블",8000,50 ,"미장센",subCategoryH.getCategory(),subCategoryH);
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


        private Makeup createMakeup(String name,int price,int stockQuantity,String brandName,Category category,SubCategory subCategory){
            Makeup makeup = new Makeup();
            makeup.setName(name);
            makeup.setPrice(price);
            makeup.setStockQuantity(stockQuantity);
            makeup.setCategory(category);
            makeup.setSubCategory(subCategory);
            makeup.setBrandName(brandName);
            return makeup;
        }

        private SkinCare createSkinCare(String name,int price,int stockQuantity,String brandName,Category category,SubCategory subCategory){
            SkinCare skinCare = new SkinCare();
            skinCare.setName(name);
            skinCare.setPrice(price);
            skinCare.setStockQuantity(stockQuantity);
            skinCare.setCategory(category);
            skinCare.setSubCategory(subCategory);
            skinCare.setBrandName(brandName);

            return skinCare;
        }

        private HairItem createHairItem(String name,int price,int stockQuantity,String brandName ,Category category,SubCategory subCategory){
            HairItem hairItem = new HairItem();
            hairItem.setName(name);
            hairItem.setPrice(price);
            hairItem.setStockQuantity(stockQuantity);
            hairItem.setBrandName(brandName);
            hairItem.setCategory(category);
            hairItem.setSubCategory(subCategory);
            hairItem.setCategory(category);
            return hairItem;
        }

        private Delivery createDelivery(Member member){
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
