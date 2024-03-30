package cosmetics.BOGOShop.controller;

import cosmetics.BOGOShop.domain.item.Makeup;
import cosmetics.BOGOShop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
}
