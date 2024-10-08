package cosmetics.BOGOShop.repository.order.optimization;


import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import cosmetics.BOGOShop.domain.QDelivery;
import cosmetics.BOGOShop.domain.QMember;
import cosmetics.BOGOShop.domain.QOrder;
import cosmetics.BOGOShop.domain.QOrderItem;
import cosmetics.BOGOShop.domain.item.QItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 컬렉션은 별도로 조회
     * Query : 루트 1번, 컬렉션 N번
     * 단건 조회에서 많이 사용하는 방식
     */
    public List<OrderQueryDto> findOrderQueryDtos(int offset, int limit){
        //루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders(offset, limit);

        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행)
        result.forEach( o-> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return result;
    }

    /**
     * 1:N 관계 (컬렉션)를 제외한 나머지를 한번에 조회
     */
    private List <OrderQueryDto> findOrders(int offset, int limit){
        return queryFactory
                .select(new QOrderQueryDto(
                        QOrder.order.id,
                        QMember.member.name,
                        QOrder.order.orderDate,
                        QOrder.order.status,
                        QDelivery.delivery.address
                ))
                .from(QOrder.order)
                .join(QOrder.order.member, QMember.member)
                .join(QOrder.order.delivery,QDelivery.delivery)
                .offset(offset) // 페이징 적용
                .limit(limit)   // 페이징 적용
                .fetch();
    }


    /**
     * 1:N 관계인 orderItems 조회
     */
    private List<OrderItemQueryDto> findOrderItems(Long orderIds){
        return queryFactory
                .select(new QOrderItemQueryDto(
                       QOrderItem.orderItem.order.id,
                        QItem.item.name,
                        QOrderItem.orderItem.orderPrice,
                        QOrderItem.orderItem.count
                ))
                .from(QOrderItem.orderItem)
                .join(QOrderItem.orderItem.item, QItem.item)
                .where(QOrderItem.orderItem.order.id.eq(orderIds))
                .fetch();
    }

    /**
     * 최적화
     * Query: 루트 1번, 컬렉션 1번
     * 데이터를 한꺼번에 처리할 때 많이 사용하는 방식
     */
    public List<OrderQueryDto> findAllByDto_optimization(int offset, int limit) {
        //루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders(offset,limit);

        //orderItem 컬렉션을 MAP 한방에 조회
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행)
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result){
        return result.stream()
                .map(o ->o.getOrderId())
                .collect(Collectors.toList());
    }

    private Map<Long,List<OrderItemQueryDto>> findOrderItemMap(List<Long> ordersIds){
        List<OrderItemQueryDto> orderItems = queryFactory
                .select(new QOrderItemQueryDto(
                        QOrderItem.orderItem.order.id,
                        QItem.item.name,
                        QOrderItem.orderItem.orderPrice,
                        QOrderItem.orderItem.count
                ))
                .from(QOrderItem.orderItem)
                .join(QOrderItem.orderItem.item, QItem.item)
                .where(QOrderItem.orderItem.order.id.in(ordersIds))
                .fetch();
        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderItemId));
    }


}
