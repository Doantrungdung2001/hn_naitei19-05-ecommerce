package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller("payment")
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

//    @CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping(value = "/vnpay")
    public String VnPayPayment(Model model,
                               @RequestParam("totalPirce") long totalPrice) throws UnsupportedEncodingException {
        if (totalPrice > 0) {
            try {
                String paymentUrl = paymentService.VnpayPayment(totalPrice);
                if (paymentUrl != null) {
                    // Payment URL obtained successfully, redirect the user to the payment page.
                    return "redirect:" + paymentUrl;
                } else {
                    // Handle the case where paymentService.VnpayPayment returns null
                    model.addAttribute("alert", "fail");
                    return "errors/404"; // Or another appropriate error view
                }
            } catch (UnsupportedEncodingException e) {
                // Handle the encoding exception
                model.addAttribute("alert", "fail");
                return "errors"; // Or another appropriate error view
            }
        } else {
            // Handle the case where totalPrice is not greater than 0
            model.addAttribute("alert", "fail");
            return "errors/404"; // Or another appropriate error view
        }
    }
}
