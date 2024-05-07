package cosmetics.BOGOShop.repository.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cosmetics.BOGOShop.domain.Category;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static cosmetics.BOGOShop.domain.QCategory.category;

import java.util.List;

@Repository
public class CategoryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;


    public CategoryRepository(EntityManager em, JPAQueryFactory query) {
        this.em = em;
        this.query = query;
    }

    //카테고리 저장
    public void save(Category category){
        em.persist(category);
    }

    public Category findOne(Long id){
        return em.find(Category.class, id);
    }

    //카테고리 전체보기
    public List<Category> findAll(){
        return query.selectFrom(category)
                .fetch();
    }

    //카테고리 이름 검색
    public List<Category> findByName(String name){
        return em.createQuery("select c from Category c where c.name = :name",Category.class)
                .setParameter("name",name)
                .getResultList();
    }

    //카데고리작성
    public List<Category> findALl2 (){
        return em.createQuery("select c from Category c where c.parent is NULL", Category.class)
                .getResultList();
    }

}
