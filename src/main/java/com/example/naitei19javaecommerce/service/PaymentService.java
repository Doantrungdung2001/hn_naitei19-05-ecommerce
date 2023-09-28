package com.example.naitei19javaecommerce.service;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    public String VnpayPayment (long totalPrice) throws UnsupportedEncodingException;
}
