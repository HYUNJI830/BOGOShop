package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.controller.MakeupForm;
import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.ItemFactory;
import cosmetics.BOGOShop.domain.item.Makeup;
import cosmetics.BOGOShop.dto.Result;
import cosmetics.BOGOShop.dto.category.CategoryDto;
import cosmetics.BOGOShop.dto.category.CategoryResponse;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ItemApiController {


    private final ItemService itemService;

    private final ItemFactory itemFactory;

    /**
     * 상품 등록
     */
    @PostMapping("/api/item")
    public ResponseEntity<String> saveItem(@RequestBody @Valid ItemDto itemDto) {
        Item item = itemFactory.createItem(itemDto);
        itemService.saveItem(item);
        return ResponseEntity.ok(item.getName());
    }

    /**
     * 전체상품 조회
     */
    @GetMapping("/api/items")
    public Result findItems(){
        List <Item> findItems = itemService.findItems();
        List <ItemDto> collect = findItems.stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
        return new Result(collect.size(),collect);
    }

    /**
     * 카테고리별 아이템 찾기
     * @param categoryId
     * http://localhost:8080/api/items?categoryId=2
     */
    @GetMapping("/api/item")
    public List<ItemDto> searchItem(@RequestParam(required = false) Long categoryId){
        return itemService.searchByCategory(categoryId);
    }

    /**
     * 상품 수정
     */
    @PostMapping("/api/{itemId}/edit")
    public ResponseEntity<String> updateItem(@RequestParam(required = false)Long itemId,@RequestBody @Valid ItemDto itemDto){
          itemService.updateItem(itemId,itemDto.getItemName(),itemDto.getPrice(),itemDto.getStockQuantity());
        return ResponseEntity.ok("상품 수정 완료");
    }




}
