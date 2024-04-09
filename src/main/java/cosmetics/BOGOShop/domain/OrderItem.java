package cosmetics.BOGOShop.domain;

import cosmetics.BOGOShop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="order_item")
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id") //fk
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id") //fk
    private Order order;

    private int count;//주문수량

    private int orderPrice; //주문가격

    //=생성 메서드=//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }

    public static List<OrderItem> createOrderItems(List<Item> items, List<Integer> orderPrices, List<Integer> counts){
        if(items.size() !=orderPrices.size() || items.size() != counts.size()){
            throw new IllegalStateException("아이템,주문가격,수량의 크기가 일치하지 않습니다.");
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for(int i =0; i< items.size(); i++){
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(items.get(i));
            orderItem.setOrderPrice(orderPrices.get(i));
            orderItem.setCount(counts.get(i));
            items.get(i).removeStock(counts.get(i));
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    //=비즈니스 로직=//
    /** 주문취소 */
    public void cancel(){
        getItem().addStock(count);
    }

    //=조회 로직=//
    //** 주문상품 전체 가격 조회 */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }
}
