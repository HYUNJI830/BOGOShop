package cosmetics.BOGOShop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import cosmetics.BOGOShop.domain.QCategory;
import cosmetics.BOGOShop.domain.QSubCategory;
import cosmetics.BOGOShop.domain.item.Item;

import cosmetics.BOGOShop.domain.item.QItem;
import cosmetics.BOGOShop.dto.item.ItemDto;

import cosmetics.BOGOShop.dto.item.QItemDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item); // 새로운 엔티티로 판단, 영속화
        }else {
            em.merge(item); // 병합 (준영속 > 영속)
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAllById(List<Long> ids){
        return  em.createQuery("select i from Item i where i.id in :ids",Item.class)
                .setParameter("ids",ids)
                .getResultList();
    }

    public List<Item> findALl(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }

    public List<ItemDto> searchByConditionEX(Integer stockQuantity, Integer priceMin, Integer priceMax) {

        // 모든 조건이 없는 경우
        if (stockQuantity == null && priceMin == null && priceMax == null) {
            return queryFactory
                    .select(new QItemDto(
                            QItem.item.id,
                            QItem.item.name,
                            QItem.item.brandName,
                            QItem.item.price,
                            QItem.item.stockQuantity,
                            QCategory.category.id,
                            QCategory.category.name,
                            QSubCategory.subCategory.id,
                            QSubCategory.subCategory.name
                    ))
                    .from(QItem.item)
                    .fetch();
        }

        // 수량 조건만 있는 경우
        if (stockQuantity != null && priceMin == null && priceMax == null) {
            return queryFactory
                    .select(new QItemDto(
                            QItem.item.id,
                            QItem.item.name,
                            QItem.item.brandName,
                            QItem.item.price,
                            QItem.item.stockQuantity,
                            QCategory.category.id,
                            QCategory.category.name,
                            QSubCategory.subCategory.id,
                            QSubCategory.subCategory.name
                    ))
                    .from(QItem.item)
                    .where(QItem.item.stockQuantity.goe(stockQuantity))
                    .fetch();
        }

        // 가격 조건만 있는 경우
        if (stockQuantity == null && priceMin != null && priceMax != null) {
            return queryFactory
                    .select(new QItemDto(
                            QItem.item.id,
                            QItem.item.name,
                            QItem.item.brandName,
                            QItem.item.price,
                            QItem.item.stockQuantity,
                            QCategory.category.id,
                            QCategory.category.name,
                            QSubCategory.subCategory.id,
                            QSubCategory.subCategory.name
                    ))
                    .from(QItem.item)
                    .where(QItem.item.price.between(priceMin, priceMax))
                    .fetch();
        }

        // 수량과 가격 조건이 모두 있는 경우
        if (stockQuantity != null && priceMin != null && priceMax != null) {
            return queryFactory
                    .select(new QItemDto(
                            QItem.item.id,
                            QItem.item.name,
                            QItem.item.brandName,
                            QItem.item.price,
                            QItem.item.stockQuantity,
                            QCategory.category.id,
                            QCategory.category.name,
                            QSubCategory.subCategory.id,
                            QSubCategory.subCategory.name
                    ))
                    .from(QItem.item)
                    .where(QItem.item.stockQuantity.goe(stockQuantity)
                            .or(QItem.item.price.between(priceMin, priceMax)))
                    .fetch();
        }

        return null;
    }

    public List<ItemDto> findALlV1(){
        return queryFactory
                .select(new QItemDto(
                        QItem.item.id,
                        QItem.item.name,
                        QItem.item.brandName,
                        QItem.item.price,
                        QItem.item.stockQuantity,
                        QCategory.category.id,
                        QCategory.category.name,
                        QSubCategory.subCategory.id,
                        QSubCategory.subCategory.name
                ))
                .from(QItem.item)
                .fetch();
    }

    public List<ItemDto> searchByCategory(Long categoryId) {
                return queryFactory
                .select(new QItemDto(
                        QItem.item.id,
                        QItem.item.name,
                        QItem.item.brandName,
                        QItem.item.price,
                        QItem.item.stockQuantity,
                        QCategory.category.id,
                        QCategory.category.name,
                        QSubCategory.subCategory.id,
                        QSubCategory.subCategory.name
                ))
                .from(QItem.item)
                .where(QCategory.category.id.eq(categoryId))
                .fetch();
    }
    /*
     * 동적 쿼리(상품 재고 관리)
     */
    public List<ItemDto> searchByCondition(Integer stockQuantity,Integer priceMin,Integer priceMax){

        return queryFactory
                .select(new QItemDto(
                        QItem.item.id,
                        QItem.item.name,
                        QItem.item.brandName,
                        QItem.item.price,
                        QItem.item.stockQuantity,
                        QCategory.category.id,
                        QCategory.category.name,
                        QSubCategory.subCategory.id,
                        QSubCategory.subCategory.name
                ))
                .from(QItem.item)
                .where(buildOrCondition(stockQuantity,priceMin,priceMax))
                .fetch();
    }

    private BooleanExpression conditionStockQuantity(Integer stockQuantity){
        //특정 수량 이상
        return stockQuantity != null ? QItem.item.stockQuantity.goe(stockQuantity) : null;
    }
    private BooleanExpression conditionPrice(Integer priceMin, Integer priceMax) {
       //특정 가격 이상
        return (priceMin != null && priceMax != null) ? QItem.item.price.between(priceMin, priceMax) : null;
    }

    private BooleanExpression buildOrCondition(Integer stockQuantity, Integer priceMin, Integer priceMax) {
        BooleanExpression stockCondition = conditionStockQuantity(stockQuantity);
        BooleanExpression priceCondition = conditionPrice(priceMin, priceMax);

        if (stockCondition != null && priceCondition != null) {
            return stockCondition.or(priceCondition);
        } else if (stockCondition != null) {
            return stockCondition;
        } else if (priceCondition != null) {
            return priceCondition;
        } else {
            return null;
        }
    }


    /**
     *  페이징
     */
    public Page<ItemDto> pageItem (Pageable pageable){
        QueryResults <ItemDto> results = queryFactory
                .select(new QItemDto(
                        QItem.item.id,
                        QItem.item.name,
                        QItem.item.brandName,
                        QItem.item.price,
                        QItem.item.stockQuantity,
                        QCategory.category.id,
                        QCategory.category.name,
                        QSubCategory.subCategory.id,
                        QSubCategory.subCategory.name
                ))
                .from(QItem.item)
                .fetchResults(); // 전체 카운트를 한번에 조회
        List <ItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }


   //커버링 인덱스를 사용한 페이징
    public Page<ItemDto> pageItemIndex(Pageable pageable) {
        // 1) 커버링 인덱스로 대상 조회 (ID만 조회)
        List<Long> ids = queryFactory
                .select(QItem.item.id)
                .from(QItem.item)
                .orderBy(QItem.item.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        // 1-1) 대상이 없을 경우 추가 쿼리 수행 할 필요 없이 바로 반환
        if (CollectionUtils.isEmpty(ids)) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        // 2) 조회한 ID로 필요한 데이터 조회
        List<ItemDto> content = queryFactory
                .select(Projections.constructor(ItemDto.class,
                        QItem.item.id,
                        QItem.item.name,
                        QItem.item.brandName,
                        QItem.item.price,
                        QItem.item.stockQuantity,
                        QCategory.category.id,
                        QCategory.category.name,
                        QSubCategory.subCategory.id,
                        QSubCategory.subCategory.name
                ))
                .from(QItem.item)
                .leftJoin(QItem.item.category,QCategory.category)
                .leftJoin(QItem.item.subCategory, QSubCategory.subCategory)
                .where(QItem.item.id.in(ids))
                .orderBy(QItem.item.id.desc())
                .fetch();

        // 전체 데이터 개수 조회
        long total = queryFactory
                .select(QItem.item.count())
                .from(QItem.item)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

}
