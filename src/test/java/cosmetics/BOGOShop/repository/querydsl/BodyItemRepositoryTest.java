package cosmetics.BOGOShop.repository.querydsl;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.item.BodyCare;
import cosmetics.BOGOShop.domain.item.BodyItem;
import cosmetics.BOGOShop.dto.item.BodyCareItemDto;
import cosmetics.BOGOShop.dto.item.BodyItemSearchCondition;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BodyItemRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    BodyItemRepository bodyItemRepository;

//    @Test
//    public void basicTest() {
//        BodyItem bodyItem = new BodyItem("바디아이템1","일리윤",20000);
//        bodyItemRepository.save(bodyItem);
//
//        BodyItem findBodyItem = bodyItemRepository.findById(bodyItem.getId()).get();
//        Assertions.assertThat(findBodyItem).isEqualTo(bodyItem);
//
//        List<BodyItem> findBodyItemByBrandName = bodyItemRepository.findByBrandName("일리윤");
//        Assertions.assertThat(findBodyItemByBrandName).containsExactly(bodyItem);
//    }
    @Test
    public void searchTest(){
        Category categoryA = new Category("바디케어");
        em.persist(categoryA);

        BodyCare bodyCareA = new BodyCare("로션",categoryA);
        BodyCare bodyCareB = new BodyCare("립케어",categoryA);
        em.persist(bodyCareA);
        em.persist(bodyCareB);

        BodyItem bodyItem11 = new BodyItem("리페어로션","일리윤",10000,bodyCareA);
        BodyItem bodyItem12 = new BodyItem("퍼퓸로션","부케가르니",7000,bodyCareA);
        BodyItem bodyItem21 = new BodyItem("딸기맛립밤","니베아",3000,bodyCareB);
        BodyItem bodyItem22 = new BodyItem("허니립마스크","에뛰드",8000,bodyCareB);
        em.persist(bodyItem11);
        em.persist(bodyItem12);
        em.persist(bodyItem21);
        em.persist(bodyItem22);

        BodyItemSearchCondition searchCondition = new BodyItemSearchCondition();
        searchCondition.setPriceGoe(3000);
        searchCondition.setBodyCareName("립케어");

        List<BodyCareItemDto> result = bodyItemRepository.search(searchCondition);
        Assertions.assertThat(result).extracting("bodyItemName").contains("허니립마스크","딸기맛립밤");

    }


}