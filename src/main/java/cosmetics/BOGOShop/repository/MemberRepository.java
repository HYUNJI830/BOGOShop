package cosmetics.BOGOShop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.domain.QMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

import static cosmetics.BOGOShop.domain.QMember.member;

@Repository
//@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;


    public MemberRepository(EntityManager em, JPAQueryFactory query) {
        this.em = em;
        this.query = query;
    }

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return query.selectFrom(member)
                .fetch();
//       return em.createQuery("select m from Member m",Member.class)
//                .getResultList(); //결과가 하나 이상일때, 리스트로 반환
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }

}
