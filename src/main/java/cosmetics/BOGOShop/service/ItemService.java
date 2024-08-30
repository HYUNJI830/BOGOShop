package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.pattern.ItemStrategy;
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
    private final ItemStrategy itemStrategy;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public List<Item> findItems(){
        return itemRepository.findALl();
    }

    @Transactional
    public List<ItemDto> findItemsV1(){
        return itemRepository.findALlV1();
    }

    @Transactional
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

    @Transactional
    public List<ItemDto> searchByCategory(Long categoryId) {
        return itemRepository.searchByCategory(categoryId);
    }

    @Transactional
    public List<ItemDto> searchByCondition (Integer stockQuantity,Integer priceMin,Integer priceMax) {
        return itemRepository.searchByCondition(stockQuantity,priceMin,priceMax);
    }

    @Transactional
    public Page<ItemDto> page(Pageable pageable){
        return itemRepository.pageItem(pageable);
    }

    @Transactional
    public Page<ItemDto> pageIndex(Pageable pageable){
        return itemRepository.pageItemIndex(pageable);
    }

    @Transactional
    public Item saveItemV1(ItemDto itemDto){
        return itemStrategy.createItem(itemDto);
    }
}
