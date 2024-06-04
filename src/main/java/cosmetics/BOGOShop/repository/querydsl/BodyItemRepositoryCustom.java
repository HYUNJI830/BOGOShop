package cosmetics.BOGOShop.repository.querydsl;

import cosmetics.BOGOShop.dto.item.BodyCareItemDto;
import cosmetics.BOGOShop.dto.item.BodyItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BodyItemRepositoryCustom {
    //JPA에서 자동으로 생성해주는 쿼리문이 아닌
    //동적쿼리 및 복잡한 쿼리문들 구현하기 위해 BodyItemRepositoryCustom 인터페이스로 관리

    //조건에 맞는 바디 아이템 조회
    List<BodyCareItemDto> search(BodyItemSearchCondition searchCondition);

    //페이징
    Page<BodyCareItemDto> searchPageSimple(BodyItemSearchCondition searchCondition, Pageable pageable);

    Page<BodyCareItemDto> searchPageComplex(BodyItemSearchCondition searchCondition, Pageable pageable);


}