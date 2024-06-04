package cosmetics.BOGOShop.repository.querydsl;

import cosmetics.BOGOShop.domain.item.BodyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodyItemJPARepository extends JpaRepository<BodyItem,Long>,BodyItemRepositoryCustom {
    //JPA 제공해주는 기본적인 crud는 BodyItemJPARepository에서 자동 처리
    //메소드 명으로만으로 쿼리 자동 생성

    //다만 동적 쿼리같은 복잡한 쿼리는 BodyItemRepositoryCustom 인터페이스를 상속받는 BodyItemJPARepositoryImpl에서 구현
    List<BodyItem> findByBrandName(String brandName);

}
