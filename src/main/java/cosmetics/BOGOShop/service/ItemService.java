package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.dto.Result;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<Item> findItems(){
        return itemRepository.findALl();
    }

    public Item find(Long itemId){
        return itemRepository.findOne(itemId);
    }

    /**
     *  영속성 컨텍스트가 자동 변경
     */
    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity){
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public List<ItemDto> searchByCategory(Long categoryId) {
        return itemRepository.searchByCategory(categoryId);
    }

    public Page<ItemDto> page(Pageable pageable){
        return itemRepository.pageItem(pageable);
    }
}
