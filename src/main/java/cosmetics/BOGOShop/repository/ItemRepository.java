package cosmetics.BOGOShop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cosmetics.BOGOShop.domain.QCategory;
import cosmetics.BOGOShop.domain.QSubCategory;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.QItem;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.dto.item.QItemDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item); // 새로운 엔티티로 판단, 영속화
        }else {
            em.merge(item); // 병합 (준영속 > 영속)
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAllById(List<Long> ids){
        return  em.createQuery("select i from Item i where i.id in :ids",Item.class)
                .setParameter("ids",ids)
                .getResultList();
    }

    public List<Item> findALl(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }

    public List<ItemDto> searchByCategory(Long categoryId) {
                return queryFactory
                .select(new QItemDto(
                        QItem.item.id,
                        QItem.item.name,
                        QItem.item.price,
                        QItem.item.stockQuantity,
                        QCategory.category.id,
                        QCategory.category.name,
                        QSubCategory.subCategory.id,
                        QSubCategory.subCategory.name
                ))
                .from(QItem.item)
                .where(QCategory.category.id.eq(categoryId))
                .fetch();
    }
}
