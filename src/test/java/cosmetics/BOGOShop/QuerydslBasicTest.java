package cosmetics.BOGOShop;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.item.BodyCare;
import cosmetics.BOGOShop.domain.item.BodyItem;
import cosmetics.BOGOShop.domain.item.QBodyCare;
import cosmetics.BOGOShop.domain.item.QBodyItem;
import cosmetics.BOGOShop.dto.item.BodyItemDto;
import cosmetics.BOGOShop.dto.item.QBodyItemDto;
import jakarta.persistence.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cosmetics.BOGOShop.domain.item.QBodyCare.bodyCare;
import static cosmetics.BOGOShop.domain.item.QBodyItem.*;
import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Before
    //JUnit 5에서는 @BeforeEach 사용 한다.
    public void before(){
        queryFactory = new JPAQueryFactory(em);

        Category categoryA = new Category("바디케어");
        em.persist(categoryA);

        BodyCare bodyCare_Shower = new BodyCare("Shower",categoryA);
        BodyCare bodyCare_Lip = new BodyCare("Lip",categoryA);
        em.persist(bodyCare_Shower);
        em.persist(bodyCare_Lip);

        BodyItem bodyItem1 = new BodyItem("bodyItem1","일리윤",5000,bodyCare_Shower);
        BodyItem bodyItem2 = new BodyItem("bodyItem2","온더바디",10000,bodyCare_Shower);
        BodyItem bodyItem3 = new BodyItem("bodyItem3","니베아",7000,bodyCare_Lip);
        BodyItem bodyItem4 = new BodyItem("bodyItem4","니베아",9000,bodyCare_Shower);

        em.persist(bodyItem1);
        em.persist(bodyItem2);
        em.persist(bodyItem3);
        em.persist(bodyItem4);
    }

    @Test
    public void startQueryDsl(){
        //JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        //QBodyItem bi = new QBodyItem("bi"); //1.별칭을 직접 주는 방법
        //QBodyItem bi = QBodyItem.bodyItem; //2.QBodyItem 사용하는 방법
        //3.static import 사용하는 방법 alt+enter

        BodyItem findBodyItem = queryFactory
                .select(bodyItem)
                .from(bodyItem)
                .where(bodyItem.name.eq("bodyItem1"))//파라미터 바인딩 안해도 자동으로 연결
                .fetchOne();

        assertThat(findBodyItem.getName()).isEqualTo("bodyItem1");
    }


    @Test
    public void search(){
        BodyItem findBodyItem = queryFactory
                .selectFrom(bodyItem)
                .where(bodyItem.name.eq("bodyItem1")
                .and(bodyItem.brandName.eq("일리윤")))
                .fetchOne();

        assertThat(findBodyItem.getName()).isEqualTo("bodyItem1");
    }
    @Test
    public void searchAndParam(){
        List<BodyItem> result1  = queryFactory
                .selectFrom(bodyItem)
                .where(bodyItem.name.eq("bodyItem1"),bodyItem.brandName.eq("일리윤"))
                .fetch();

        assertThat(result1.size()).isEqualTo(1);
    }

    //조회
    @Test
    public void resultFetch(){
        //다수
        List<BodyItem> fetch = queryFactory
                .selectFrom(bodyItem)
                .fetch();
        //단일 건
        BodyItem fetchOne = queryFactory
                .selectFrom(bodyItem)
                .fetchOne();
        //처음 1건 조회
        BodyItem fetchFirst = queryFactory
                .selectFrom(bodyItem)
                .fetchFirst(); //동일 : limit(1).fetchOne();

        //페이징에서 사용
        QueryResults<BodyItem> results = queryFactory
                .selectFrom(bodyItem)
                .fetchResults();//페이징 정보 포함, total Count 쿼리 추가 실행

        results.getTotal();
        List<BodyItem> content = results.getResults();

        //count 쿼리로 변경
        Long count= queryFactory
                .selectFrom(bodyItem)
                .fetchCount(); //count 쿼리
    }

    /**
     * 정렬
     */
    @Test
    public void sort(){
        List<BodyItem> result = queryFactory
                .selectFrom(bodyItem)
                .orderBy(bodyItem.price.desc(),bodyItem.brandName.asc().nullsLast())
                .fetch();

        BodyItem bodyItem4 = result.get(0);
        BodyItem bodyItem2 = result.get(1);

        assertThat(bodyItem4.getName()).isEqualTo("bodyItem4");
        assertThat(bodyItem2.getName()).isEqualTo("bodyItem2");
    }

    /**
     * 페이징
     */
    @Test
    public void paging1(){
        List<BodyItem> result = queryFactory
                .selectFrom(bodyItem)
                .orderBy(bodyItem.name.desc())
                .offset(1) //0부터 시작 (index)
                .limit(2) //최대 2건 조회
                .fetch();
        assertThat(result.size()).isEqualTo(2);

    }

    /**
     * 그룹바이
     */
    @Test
    public void group(){
        List<Tuple> result = queryFactory
                .select(bodyCare.name,bodyItem.price.avg())
                .from(bodyItem)
                .join(bodyItem.bodyCare, bodyCare)
                .groupBy(bodyCare.name)
                .fetch();

        Tuple lip = result.get(0);
        Tuple shower = result.get(1);


        assertThat(shower.get(bodyCare.name)).isEqualTo("Shower");
        assertThat(shower.get(bodyItem.price.avg())).isEqualTo(8000);

    }

    /**
     * 조인
     */
    @Test
    public void join(){
        QBodyItem bodyItem = QBodyItem.bodyItem;
        QBodyCare bodyCare = QBodyCare.bodyCare;

        List <BodyItem> result = queryFactory
                .selectFrom(bodyItem)
                .join(bodyItem.bodyCare, bodyCare)
                .where(bodyCare.name.eq("Lip"))
                .fetch();

        assertThat(result)
                .extracting("brandName")
                .containsExactly("니베아");
    }

    /**
     *조인 On 절
     */
    //내부 조인을 하는 경우 On절로 필터링 하는 것과 where 조건절의 결과가 동일하다.
    //외부 조인(연관관계가 없는 엔티티)을 하는 경우에 On절을 활용도가 높다
    //연관관계 없는 엔티티 외부 조인
//    @Test
//    public void join_on_no_relation(){
//        em.persist(new BodyItem("Shower"));
//        em.persist(new BodyItem("Lip"));
//
//        List<Tuple> result = queryFactory
//                .select(bodyItem, bodyCare)
//                .from(bodyItem)
//                .leftJoin(bodyCare).on(bodyItem.name.eq(bodyCare.name))
//                .fetch();
//
//        for(Tuple tuple : result){
//            System.out.println("tuple = " + tuple);
//        }
//    }

    /**
     * 페치 조인
     */
    @PersistenceUnit
    EntityManagerFactory emf;
    @Test
    public void fetchJoin(){
        BodyItem findBodyItem = queryFactory
                .selectFrom(bodyItem)
                .join(bodyItem.bodyCare, bodyCare).fetchJoin()
                .where(bodyItem.name.eq("bodyItem1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findBodyItem.getBodyCare());
        assertThat(loaded).as("페치 조인 적용").isTrue();
    }

    //서브쿼리
    /*
     금액이 가장 높은 제품 조회
     */
    @Test
    public void subQuery(){
        QBodyItem bodyItemSub = new QBodyItem("bodyItemSub");

        List<BodyItem> result = queryFactory
                .selectFrom(bodyItem)
                .where(bodyItem.price.eq(
                        JPAExpressions
                                .select(bodyItemSub.price.max())
                                .from(bodyItemSub)
                ))
                .fetch();
        assertThat(result).extracting("price")
                .containsExactly(10000);
    }
    /*
        서브쿼리 여러건 처리, in 사용
     */
    @Test
    public void subQueryIn(){
        QBodyItem bodyItemSub = new QBodyItem("bodyItemSub");

        List<BodyItem> result = queryFactory
                .selectFrom(bodyItem)
                .where(bodyItem.price.in(
                        JPAExpressions
                                .select(bodyItemSub.price)
                                .from(bodyItemSub)
                                .where(bodyItemSub.price.gt(5000))
                ))
                .fetch();
        assertThat(result).extracting("price").containsExactly(10000,7000,9000);
    }

    /**
     * case 문
     */


    /**
     * 상수, 문자 더하기
     */
    //문자 더하기
    @Test
    public void constant(){
        List<Tuple> result = queryFactory
                .select(bodyItem.name, Expressions.constant("바디아이템"))
                .from(bodyItem)
                .fetch();
        
        for(Tuple tuple: result){
            System.out.println("tuple = " + tuple);
        }
        //tuple = [bodyItem1, 바디아이템]
    }
    @Test
    public void concat(){
        //{username}_{age}
        String result = queryFactory
                .select(bodyItem.name.concat("_").concat(bodyItem.price.stringValue()))
                .from(bodyItem)
                .where(bodyItem.name.eq("bodyItem1"))
                .fetchOne();

        System.out.println("result = " + result);
    }

    /**
     * 프로젝션 - Tuple 조회
     */
    //참고로 Tuple은 레포지토리 안에서만 사용하고 밖으로 나갈 때는 DTO로 변한해서 사용하는 것 권장한다.
    //-> 쿼리dsl에서 다른 방법으로 변경할 때 유지보수가 많이 들기 때문
    @Test
    public void tupleProjection(){
        List<Tuple> result = queryFactory
                .select(bodyItem.name, bodyItem.price)
                .from(bodyItem)
                .fetch();
        for(Tuple tuple : result){
            String userName = tuple.get(bodyItem.name);
            Integer age = tuple.get(bodyItem.price);
            System.out.println("userName = " + userName + " ,age = "+ age);
        }
    }
    /**
     * 프로젝션 - DTO 조회
     */
    //주의사항 : constructor 생성자로 사용하는 경우 해당 DTO에 select 문에서 조회할 대상과 정확히 일치해야함
    //예를 들어 DTO의 생성자가 a,b,c가 있는데, Projections.constructor에서 a,b 조회하는 경우 오류가 발생함

    //다만, 프로퍼티접근법(Projections.bean)이나 필드 접근법(Projections.fields)은 dto의 생성자를
    //조회하는 방법이 아니기때문에 상관없다.

    //프로퍼티 접근법
    @Test
    public void findDtoQuerySetter(){
        List<BodyItemDto> result = queryFactory
                .select(Projections.bean(BodyItemDto.class,
                        bodyItem.name,
                        bodyItem.price))
                .from(bodyItem)
                .fetch();
        for(BodyItemDto bodyItem : result){
            System.out.println(bodyItem);
        }
    }
    //필드 직접 접근
    @Test
    public void findDtoQueryField(){
        List<BodyItemDto> result = queryFactory
                .select(Projections.fields(BodyItemDto.class,
                        bodyItem.name,
                        bodyItem.price))
                .from(bodyItem)
                .fetch();
        for(BodyItemDto bodyItem : result){
            System.out.println(bodyItem);
        }
    }

    //생성자 사용
    //런타임 시점에서 오류발생 알 수 있음
    @Test
    public void findDtoQueryConstructor(){
        List<BodyItemDto> result = queryFactory
                .select(Projections.constructor(BodyItemDto.class,
                        bodyItem.name,
                        bodyItem.price))
                .from(bodyItem)
                .fetch();
        for(BodyItemDto bodyItem : result){
            System.out.println(bodyItem);
        }
    }
    //@QueryProjection
    //컴파일 시점에서 오류발생 알 수 있음
    @Test
    public void findDtoQueryProjection(){
        List<BodyItemDto> result = queryFactory
                .select(new QBodyItemDto(bodyItem.name,bodyItem.price))
                .from(bodyItem)
                .fetch();
        for(BodyItemDto bodyItem : result){
            System.out.println(bodyItem);
        }
    }
    /**
     * 동적쿼리-BooleanBuilder
     */
    @Test
    public void 동적쿼리_BooleanBuilder(){
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<BodyItem> result = searchBodyItem1(usernameParam,ageParam);
    }

    private List<BodyItem> searchBodyItem1(String usernameCond, Integer ageCond){

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(usernameCond != null){
            booleanBuilder.and(bodyItem.name.eq(usernameCond));
        }
        if(ageCond !=null){
            booleanBuilder.and(bodyItem.price.eq(ageCond));
        }

        return queryFactory
                .selectFrom(bodyItem)
                .where(booleanBuilder)
                .fetch();
    }

    @Test
    public void 동적쿼리_WhereParam(){
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<BodyItem> result = searchBodyItem2(usernameParam,ageParam);
    }

    private List<BodyItem> searchBodyItem2(String usernameCond, Integer ageCond){

        return queryFactory
                .selectFrom(bodyItem)
                .where(bodyItemNameEq(usernameCond),bodyItemAgeEq(ageCond))
                .fetch();
    }

    private BooleanExpression bodyItemNameEq(String usernameCond){
        return usernameCond != null ? bodyItem.name.eq(usernameCond) : null;
    }
    private BooleanExpression bodyItemAgeEq(Integer ageCond){
        return ageCond != null ? bodyItem.price.eq(ageCond) : null;
    }

    /**
     * 수정, 삭제 벌크 연산
     */
    @Test
    @Commit
    public void bulkUpdate(){
        long count = queryFactory
                .update(bodyItem)
                .set(bodyItem.name,"최저가")
                .where(bodyItem.price.eq(5000))
                .execute();

        //영속성 컨텍스트란
        //엔티티 객체의 상태를 관리하는 환경
        //엔티티 객체는 db에 저장되거나 db부터 조회된 객체를 의미한다.
        //영속성 컨텍스트는 엔티티 객체의 상태 변화를 감지하고,이러한 변화를 db에 반영한다.
        //flush() : 영속성 컨텍스트에 있는 모든 엔티티의 변경 내용을 데이터베이스에 동기화 하는 역할
        //트랜잭션이 끝나기 전에 변경 내용을 즉시 데이터베이스에 반영할 때 유용함
        em.flush();
        //clear() : 현재 영속성컨텍스트를 비우는 역할, 영속성 컨테스트에 있는 모든 엔티티 객체를 관리목록에서 제거
        em.clear();

        List<BodyItem> result = queryFactory
                .selectFrom(bodyItem)
                .fetch();
        
        for (BodyItem bodyItem1 :result){
            System.out.println("bodyItem1 = " + bodyItem1);
        }


    }
    @Test
    public void bulkAdd(){
        long count = queryFactory
                .update(bodyItem)
                .set(bodyItem.price, bodyItem.price.add(1000))
                .execute();

    }

    @Test
    public void sqlFunction(){
        List<String> result = queryFactory
                .select(Expressions.stringTemplate(
                        "function('replace',{0},{1},{2})",bodyItem.name,"bodyItem","바디아이템"))
                .from(bodyItem)
                .fetch();
        for(String s :result){
            System.out.println("s = " + s);
        }
    }


}
