package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.model.CartItem;

public interface CartItemService extends BaseService<Long, CartItem>{
    public void addItem(CartItem item);
    public void removeItem(CartItem item);

    public CartItem findItemById(Long id);
}
