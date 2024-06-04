package cosmetics.BOGOShop.domain.item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BodyItemTest {
    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity(){
        BodyCare bodyCare_Shower = new BodyCare("Shower");
        BodyCare bodyCare_Lip = new BodyCare("Lip");
        em.persist(bodyCare_Shower);
        em.persist(bodyCare_Lip);

        BodyItem bodyItem1 = new BodyItem("bodyItem1","일리윤",5000,bodyCare_Shower);
        BodyItem bodyItem2 = new BodyItem("bodyItem2","온더바디",10000,bodyCare_Shower);
        BodyItem bodyItem3 = new BodyItem("bodyItem3","니베아",7000,bodyCare_Lip);

        em.persist(bodyItem1);
        em.persist(bodyItem2);
        em.persist(bodyItem3);

        //초기화
        em.flush();
        em.clear();

        //확인
        List<BodyItem> items = em.createQuery("select bi from BodyItem bi", BodyItem.class)
                .getResultList();
        for(BodyItem bodyItem : items){
            System.out.println("바디아이템 = " + bodyItem);
            System.out.println("-> 바디아이템.바디케어 = " + bodyItem.getBodyCare());
        }
    }
}