package com.janith.Service;

import com.janith.model.Category;
import com.janith.model.Computer;
import com.janith.model.Shop;
import com.janith.repository.ComputerRepository;
import com.janith.request.CreateComputerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComputerServiceImp implements ComputerService{

    @Autowired
    private ComputerRepository computerRepository;



    @Override
    public Computer createComputer(CreateComputerRequest req, Category category, Shop shop) {
        Computer computer = new Computer();
        computer.setComputerCategory(category);
        computer.setShop(shop);
        computer.setDescription(req.getDescription());
        computer.setImages(req.getImages());
        computer.setName(req.getName());
        computer.setPrice(req.getPrice());
        computer.setIncludedComponents(req.getIncludedComponents());

        computer.setSeasonal(req.isSeasonal());
        computer.setBusinessUser(req.isBusinessUser());
        computer.setGamer(req.isGamer());
        computer.setDesigner(req.isDesigner());
        computer.setDeveloper(req.isDeveloper());
        computer.setHomeUser(req.isHomeUser());

        Computer savedComputer = computerRepository.save(computer);
        shop.getComputers().add(savedComputer);

        return savedComputer;
    }

    @Override
    public void deleteComputer(Long computerId) throws Exception {

        Computer computer = findComputerById(computerId);
        computer.setShop(null);
        computerRepository.save(computer);

    }

    @Override
    public List<Computer> getShopComputers(Long shopId,
                                           boolean isGamer,
                                           boolean isHomeUser,
                                           boolean isBusinessUser,
                                           boolean isDesigner,
                                           boolean isDeveloper,
                                           boolean isSeasonal,
                                           String computerCategory) {

        List<Computer> computers = computerRepository.findByShopId(shopId);
        return computers;
    }

    @Override
    public List<Computer> searchComputer(String keyword) {
        return List.of();
    }

    @Override
    public Computer findComputerById(Long computerId) throws Exception {
        return null;
    }

    @Override
    public Computer updateAvailabilityStatus(Long computerId) throws Exception {
        return null;
    }
}
