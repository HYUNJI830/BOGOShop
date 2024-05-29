package cosmetics.BOGOShop.repository;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.domain.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }


    //전체 주문 조회
    public List <Order> findAll(){
        return em.createQuery("select o from Order o",Order.class)
                .getResultList();
    }

    //주문 검색 기능
    public List<Order> findAllByCondition(OrderSearch orderSearch){
        return em.createQuery("select  o from Order o join o.member m" +
                " where o.status = :status " +
                " and m.name like :name ", Order.class)
                .setParameter("status",orderSearch.getOrderStatus())
                .setParameter("name",orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();
    }

    public List<Order> findAllWithItem() {
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", Order.class)
                .getResultList();
        //distinct : 1. DB에 distinct 키워드 날려주고, 2. 엔티티가 중복인 경우 제거 (jpa에서 자체적으로 Order 같은 ID 값이면 중복을 제거)
    }

    public List<Order>  findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


}
