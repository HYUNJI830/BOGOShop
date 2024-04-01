package cosmetics.BOGOShop.controller;

import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.Makeup;
import cosmetics.BOGOShop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new MakeupForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(MakeupForm form){
        Makeup makeup = new Makeup();
        makeup.setName(form.getName());
        makeup.setPrice(form.getPrice());
        makeup.setStockQuantity(form.getStockQuantity());
        makeup.setMakeupCategory(form.getMakeupCategory());
        makeup.setMakeupCategory(form.getMakeupCategory());

        itemService.saveItem(makeup);
        return  "redirect:/";
    }
    /**
     * 상품 목록
     */
    @GetMapping(value="/items")
    public String List(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping(value="/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Makeup item = (Makeup) itemService.findOne(itemId);
        MakeupForm form = new MakeupForm();

        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setMakeupCategory(item.getMakeupCategory());
        form.setBrandName(item.getBrandName());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }
    /**
     * 상품 수정, 권장 코드
     */
    @PostMapping(value = "/items/{itemsId}/edit")
    public String updateItem(@PathVariable Long itemsId,@ModelAttribute("form")MakeupForm form){

        itemService.updateItem(itemsId,form.getName(), form.getPrice(), form.getStockQuantity());
//        Makeup makeup = new Makeup();
//        makeup.setId(form.getId());
//        makeup.setName(form.getName());
//        makeup.setPrice(form.getPrice());
//        makeup.setStockQuantity(form.getStockQuantity());
//        makeup.setMakeupCategory(form.getMakeupCategory());
//        makeup.setBrandName(form.getBrandName());

        //itemService.saveItem(makeup);
        return "redirect:/items";
    }

}
