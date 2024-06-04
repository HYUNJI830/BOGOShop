package cosmetics.BOGOShop.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import cosmetics.BOGOShop.domain.item.BodyCare;
import cosmetics.BOGOShop.domain.item.BodyItem;
import cosmetics.BOGOShop.domain.item.QBodyCare;
import cosmetics.BOGOShop.domain.item.QBodyItem;
import cosmetics.BOGOShop.dto.item.BodyCareItemDto;
import cosmetics.BOGOShop.dto.item.BodyItemSearchCondition;
import cosmetics.BOGOShop.dto.item.QBodyCareItemDto;
import cosmetics.BOGOShop.repository.order.query.QOrderItemQueryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.isEmpty;


@Repository
@RequiredArgsConstructor
public class BodyItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

//    public BodyItemRepository(EntityManager em,JPAQueryFactory queryFactory) {
//        this.em = em;
//        this.queryFactory = queryFactory;
//    }

    public void CareSave(BodyCare bodyCare){
        em.persist(bodyCare);
    }
    public void save(BodyItem bodyItem){
        em.persist(bodyItem);
    }
    public Optional<BodyItem> findById(Long id){
        BodyItem findBodyItem = em.find(BodyItem.class,id);
        return Optional.ofNullable(findBodyItem);
    }

    public List<BodyItem> findAll(){
        return queryFactory
                .selectFrom(QBodyItem.bodyItem)
                .fetch();
    }

    public List<BodyItem> findByBrandName(String brandName){
        return queryFactory
                .selectFrom(QBodyItem.bodyItem)
                .where(QBodyItem.bodyItem.brandName.eq(brandName))
                .fetch();
    }

    public List<BodyCareItemDto> search(BodyItemSearchCondition searchCondition){
        return queryFactory
                .select(new QBodyCareItemDto(
                        QBodyCare.bodyCare.id,
                        QBodyCare.bodyCare.name,
                      QBodyItem.bodyItem.id,
                      QBodyItem.bodyItem.name,
                      QBodyItem.bodyItem.price,
                      QBodyItem.bodyItem.brandName
                ))
                .from(QBodyItem.bodyItem)
                .leftJoin(QBodyItem.bodyItem.bodyCare,QBodyCare.bodyCare)
                .where(bodyCareEq(searchCondition.getBodyCareName()),
                        bodyItemEq(searchCondition.getBodyItemName()),
                        priceGoe(searchCondition.getPriceGoe())
                )
                .fetch();
    }

    private BooleanExpression bodyCareEq(String bodyCareName){
        return isEmpty(bodyCareName) ? null : QBodyCare.bodyCare.name.eq(bodyCareName);
    }
    private BooleanExpression bodyItemEq(String bodyItemName){
        return isEmpty(bodyItemName) ? null : QBodyItem.bodyItem.name.eq(bodyItemName);
    }
    private BooleanExpression priceGoe(Integer priceGoe){
        return priceGoe == null ? null : QBodyItem.bodyItem.price.goe(priceGoe);
    }

}
