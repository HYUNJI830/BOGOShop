package cosmetics.BOGOShop.controller;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.domain.Order;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.repository.OrderSearch;
import cosmetics.BOGOShop.service.ItemService;
import cosmetics.BOGOShop.service.MemberService;
import cosmetics.BOGOShop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @GetMapping(value = "/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

//    @PostMapping(value = "/order")
//    public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count){
//        orderService.order(memberId,itemId,count);
//        return "redirect:/orders";
//    }

    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemIds") List<Long> itemIds,
                        @RequestParam("count") List<Integer> counts){
        logger.info("아이템 아이디 수량"+itemIds.size());

        if (itemIds.size() == 1) {
            // 하나의 상품을 처리하는 메소드 호출
            orderService.order(memberId, itemIds.get(0), counts.get(0));
        } else {
            // 다수의 상품을 처리하는 메소드 호출
            orderService.orders(memberId, itemIds, counts);
        }
        return "redirect:/orders";
    }

    @GetMapping(value="/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable ("orderId") Long orderID){
        orderService.cancelOrder(orderID);
        return "redirect:/orders";
    }
}
