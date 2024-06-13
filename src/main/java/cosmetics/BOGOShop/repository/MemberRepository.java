package cosmetics.BOGOShop.repository;

import cosmetics.BOGOShop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>,MemberRepositoryCustom {
    List<Member> findByName(String name);
    Optional <Member> findByUserId(String userId);


}
