package cosmetics.BOGOShop.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import cosmetics.BOGOShop.domain.item.BodyItem;
import cosmetics.BOGOShop.domain.item.QBodyCare;
import cosmetics.BOGOShop.domain.item.QBodyItem;
import cosmetics.BOGOShop.dto.item.BodyCareItemDto;
import cosmetics.BOGOShop.dto.item.BodyItemSearchCondition;
import cosmetics.BOGOShop.dto.item.QBodyCareDto;
import cosmetics.BOGOShop.dto.item.QBodyCareItemDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;


import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;


//진짜 조심할것! 인터페이스인 BodyItemJPARepository이름 맞춰야 동작됨!
public class BodyItemJPARepositoryImpl implements BodyItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BodyItemJPARepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BodyCareItemDto> search(BodyItemSearchCondition searchCondition) {
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


    @Override
    public Page<BodyCareItemDto> searchPageSimple(BodyItemSearchCondition searchCondition, Pageable pageable) {
        //content, page 같이
        QueryResults <BodyCareItemDto> result = queryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<BodyCareItemDto> content = result.getResults(); // 컨텐츠 결과
        Long total = result.getTotal(); //결과의 갯수
        return  new PageImpl<>(content,pageable,total);

    }

    @Override
    public Page<BodyCareItemDto> searchPageComplex(BodyItemSearchCondition searchCondition, Pageable pageable) {
        //content 따로
        List<BodyCareItemDto> content = queryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        //page 따로
        JPAQuery<BodyItem> countQuery = queryFactory
                .selectFrom(QBodyItem.bodyItem)
                .leftJoin(QBodyItem.bodyItem.bodyCare, QBodyCare.bodyCare)
                .where(bodyCareEq(searchCondition.getBodyCareName()),
                        bodyItemEq(searchCondition.getBodyItemName()),
                        priceGoe(searchCondition.getPriceGoe())
                );


        return PageableExecutionUtils.getPage(content,pageable,()->countQuery.fetchCount());
        //return new PageImpl<>(content,pageable,total);
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
