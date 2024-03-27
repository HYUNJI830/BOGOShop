package cosmetics.BOGOShop.repository;

import cosmetics.BOGOShop.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else {
            em.merge(item); //강제 업데이트
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findALl(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
