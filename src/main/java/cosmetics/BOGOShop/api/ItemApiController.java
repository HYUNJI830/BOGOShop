package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.controller.MakeupForm;
import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.ItemFactory;
import cosmetics.BOGOShop.domain.item.Makeup;
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

import java.util.List;

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
        return ResponseEntity.ok("Item saved successfully");
    }


}
