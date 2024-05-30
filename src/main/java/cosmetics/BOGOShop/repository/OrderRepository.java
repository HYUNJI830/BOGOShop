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



}
