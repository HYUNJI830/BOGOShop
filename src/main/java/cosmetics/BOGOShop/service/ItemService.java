package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findAll(){
        return itemRepository.findALl();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
