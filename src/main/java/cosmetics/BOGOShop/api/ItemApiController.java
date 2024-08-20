package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.controller.MakeupForm;
import cosmetics.BOGOShop.document.ItemControllerDocs;
import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.ItemFactory;
import cosmetics.BOGOShop.domain.item.Makeup;
import cosmetics.BOGOShop.dto.Result;
import cosmetics.BOGOShop.dto.category.CategoryDto;
import cosmetics.BOGOShop.dto.category.CategoryResponse;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController implements ItemControllerDocs {


    private final ItemService itemService;

    private final ItemFactory itemFactory;

    //ItemControllerDocs 구현
    @PostMapping("/api/item")
    public ResponseEntity<String> saveItem(@RequestBody @Valid ItemDto itemDto) {
        Item item = itemFactory.createItem(itemDto);
        itemService.saveItem(item);
        return ResponseEntity.ok(item.getName());
    }
    @PostMapping("/api/item/v1")
    public ResponseEntity<String> saveItemV1(@RequestBody @Valid ItemDto itemDto) {
        Item registeredItem = itemService.saveItemV1(itemDto);
        return ResponseEntity.ok(registeredItem.getName());
    }

    @Operation(summary = "전체 상품 조회", description = "전체 상품을 조회 합니다.")
    @GetMapping("/api/items")
    public Result findItems(){
        List <Item> findItems = itemService.findItems();
        List <ItemDto> collect = findItems.stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
        return new Result(collect.size(),collect);
    }

    @Operation(summary = "전체 상품 조회", description = "전체 상품을 조회 합니다.")
    @GetMapping("/api/items/v1")
    public List<ItemDto> findItemsV1(){
        return itemService.findItemsV1();
    }


    @Operation(summary = "카테고리별 상품 조회", description = "파라미터로 카테고리 ID를 전송하여 카테고리별 상품 리스트를 조회 합니다.")
    @Parameter(name = "categoryId", description = "조회하려는 카데고리 아이디")
    @GetMapping("/api/item")
    public List<ItemDto> searchItem(@RequestParam(name = "categoryId", required = false) Long categoryId){
        return itemService.searchByCategory(categoryId);
    }

    @Operation(summary = "조건별 상품 조회", description = "조건에 맞는 상품 리스트를 동적으로 조회 합니다.")
    @Parameter(name = "stockQuantity", description = "조회하려는 상품 재고 수량")
    @Parameter(name = "priceMin", description = "조회하려는 상품 재고 수량")
    @Parameter(name = "priceMax", description = "조회하려는 상품 재고 수량")
    @GetMapping("/api/item/condition")
    public List<ItemDto> conditionItem(@RequestParam(name = "stockQuantity", required = false) Integer stockQuantity,@RequestParam(name = "priceMin", required = false)Integer priceMin,@RequestParam(name = "priceMax",required = false)Integer priceMax){

        return itemService.searchByCondition(stockQuantity,priceMin,priceMax);
    }

    //ItemControllerDocs 구현
    @PostMapping("/api/{itemId}/edit")
    public ResponseEntity<String> updateItem(@RequestParam(required = false)Long itemId,@RequestBody @Valid ItemDto itemDto){
          itemService.updateItem(itemId,itemDto.getItemName(),itemDto.getPrice(),itemDto.getStockQuantity());
        return ResponseEntity.ok("상품 수정 완료");
    }

    @Operation(summary = "상품 조회(페이징)", description = "페이징을 이용해서 상품 리스트를 조회 합니다.")
    @GetMapping("/api/items/Page")
    public Page<ItemDto> pageItem(Pageable pageable){
        return itemService.page(pageable);
    }

}
