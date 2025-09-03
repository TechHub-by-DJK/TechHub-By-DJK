package com.janith.Service;

import com.janith.dto.ShopDto;
import com.janith.model.Address;
import com.janith.model.Shop;
import com.janith.model.User;
import com.janith.repository.AddressRepository;
import com.janith.repository.ShopRepository;
import com.janith.repository.UserRepository;
import com.janith.request.CreateShopRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImp implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Shop createShop(CreateShopRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());

        Shop shop = new Shop();
        shop.setAddress(address);
        shop.setContactInformation(req.getContactInformation());
        shop.setBuildingtype(req.getBuildingtype());
        shop.setDescription(req.getDescription());
        shop.setImages(req.getImages());
        shop.setName(req.getName());
        shop.setOpeningHours(req.getOpeningHours());
        shop.setRegistrationDate(LocalDateTime.now());
        shop.setOwner(user);

        return shopRepository.save(shop);
    }

    @Override
    public Shop updateShop(Long shopId, CreateShopRequest updatedShop) throws Exception {
        Shop shop = findShopById(shopId);

        if (updatedShop.getBuildingtype() != null) {
            shop.setBuildingtype(updatedShop.getBuildingtype());
        }
        if (updatedShop.getDescription() != null) {
            shop.setDescription(updatedShop.getDescription());
        }
        if (updatedShop.getName() != null) {
            shop.setName(updatedShop.getName());
        }
        if (updatedShop.getContactInformation() != null) {
            shop.setContactInformation(updatedShop.getContactInformation());
        }
        if (updatedShop.getOpeningHours() != null) {
            shop.setOpeningHours(updatedShop.getOpeningHours());
        }
        if (updatedShop.getImages() != null) {
            shop.setImages(updatedShop.getImages());
        }
        if (updatedShop.getAddress() != null) {
            // Replace or set address and persist
            Address saved = addressRepository.save(updatedShop.getAddress());
            shop.setAddress(saved);
        }
        return shopRepository.save(shop);
    }

    @Override
    public void deleteShop(Long shopId) throws Exception {
        Shop shop = findShopById(shopId);

        shopRepository.delete(shop);
    }

    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public List<Shop> searchShop(String keyword) {
        return shopRepository.findBySearchQuery(keyword);
    }

    @Override
    public Shop findShopById(Long id) throws Exception {
        Optional<Shop> opt = shopRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Shop not found with id " + id);
        }

        return opt.get();
    }

    @Override
    public Shop getShopByUserId(Long userId) throws Exception {
        Shop shop = shopRepository.findByOwnerId(userId);
        if(shop == null){
            throw new Exception("Shop not found with owner id " + userId);
        }
        return shop;
    }

    @Override
    public ShopDto addToFavourites(Long shopId, User user) throws Exception {
        Shop shop = findShopById(shopId);

        ShopDto dto = new ShopDto();
        dto.setDescription(shop.getDescription());
        dto.setImages(shop.getImages());
        dto.setTitle(shop.getName());
        dto.setId(shopId);

        boolean isFavourited = false;
        List<ShopDto> favourites = user.getFavorites();
        for(ShopDto favourite : favourites){
            if(favourite.getId().equals(shopId)){
                isFavourited = true;
                break;
            }
        }

        if(isFavourited){
            favourites.removeIf(favourite -> favourite.getId().equals(shopId));
        }else{
            favourites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Shop updateShopStatus(Long id) throws Exception {
        Shop shop = findShopById(id);
        shop.setOpen(!shop.isOpen());
        return shopRepository.save(shop);
    }

    @Override
    public Shop addShopImage(Long shopId, String imageUrl) throws Exception {
        Shop shop = findShopById(shopId);
        if (shop.getImages() == null) {
            shop.setImages(new ArrayList<>());
        }
        shop.getImages().add(imageUrl);
        return shopRepository.save(shop);
    }
}
