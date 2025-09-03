package com.janith.Service;

import com.janith.dto.ShopDto;
import com.janith.model.Shop;
import com.janith.model.User;
import com.janith.request.CreateShopRequest;

import java.util.List;

public interface ShopService {
    public Shop createShop(CreateShopRequest req, User user);

    public Shop updateShop(Long shopId, CreateShopRequest updatedShop) throws Exception;

    public void deleteShop(Long shopId) throws Exception;

    public List<Shop> getAllShops();

    public List<Shop> searchShop(String keyword);

    public Shop findShopById(Long id) throws Exception;

    public Shop getShopByUserId(Long userId) throws Exception;

    public ShopDto addToFavourites(Long shopId, User user) throws Exception;

    public Shop updateShopStatus(Long id) throws Exception;

    // Append an image to a shop and persist
    public Shop addShopImage(Long shopId, String imageUrl) throws Exception;
}
