package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.service.Impl.CartItemServiceImpl;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartItemServiceImpl cartItemServiceImpl;
    @RequestMapping("/cart")
    public String ViewCart(Model model) {
        List<CartItem> itemsList = cartItemServiceImpl.findAll();
        int totalQuantity = cartItemServiceImpl.totalQuantityItem(itemsList);
        BigDecimal totalPrice =cartItemServiceImpl.calculateTotalCartPrice(itemsList);
        model.addAttribute("itemsList",itemsList);
        model.addAttribute("totalQuantity",totalQuantity);
        model.addAttribute("totalPrice",totalPrice);
        return "user/cart/layout-cart";
    }
}
