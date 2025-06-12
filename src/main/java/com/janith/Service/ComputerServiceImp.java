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
import java.util.Optional;
import java.util.stream.Collectors;

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
        computer.setBrand(req.getBrand());
        computer.setCpu(req.getCpu());
        computer.setRam(req.getRam());
        computer.setStorage(req.getStorage());
        computer.setGpu(req.getGpu());
        computer.setOperatingSystem(req.getOperatingSystem());
        computer.setRating(req.getRating());
        computer.setStockQuantity(req.getStockQuantity());
        if (req.getComputerType() != null) {
            try {
                computer.setComputerType(Computer.ComputerType.valueOf(req.getComputerType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                computer.setComputerType(null); // or handle invalid type as needed
            }
        }
        computer.setCreatedAt(new java.util.Date());
        computer.setUpdatedAt(new java.util.Date());

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

        if (isBusinessUser){
            computers = filterByBusinessUser(computers, isBusinessUser);
        }

        if (isGamer){
            computers = filterByGamer(computers, isGamer);
        }
        
        if (isHomeUser){
            computers = filterByHomeUser(computers, isHomeUser);
        }
        
        if (isDeveloper){
            computers = filterbyDeveloper(computers, isDeveloper);
        }
        if (isDesigner){
            computers = filterbyDesigner(computers, isDesigner);
        }
        
        if (isSeasonal){
            computers = filterbyIsSeasonal(computers, isSeasonal);
        }

        if (computerCategory != null && !computerCategory.equals("")){
            computers = filterByCategory(computers, computerCategory);
        }

        return computers;
    }

    private List<Computer> filterByCategory(List<Computer> computers, String computerCategory) {
        return computers.stream().filter(computer -> {
            if (computer.getComputerCategory()!=null){
                return computer.getComputerCategory().getName().equals(computerCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Computer> filterbyIsSeasonal(List<Computer> computers, boolean isSeasonal) {
        return computers.stream().filter(computer -> computer.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Computer> filterbyDesigner(List<Computer> computers, boolean isDesigner) {
        return computers.stream().filter(computer -> computer.isDesigner() == isDesigner).collect(Collectors.toList());
    }

    private List<Computer> filterbyDeveloper(List<Computer> computers, boolean isDeveloper) {
        return computers.stream().filter(computer -> computer.isDeveloper() == isDeveloper).collect(Collectors.toList());
    }

    private List<Computer> filterByHomeUser(List<Computer> computers, boolean isHomeUser) {
        return computers.stream().filter(computer -> computer.isHomeUser() == isHomeUser).collect(Collectors.toList());
    }

    private List<Computer> filterByGamer(List<Computer> computers, boolean isGamer) {
        return computers.stream().filter(computer -> computer.isGamer() == isGamer).collect(Collectors.toList());
    }

    private List<Computer> filterByBusinessUser(List<Computer> computers, boolean isBusinessUser) {
        return computers.stream().filter(computer -> computer.isBusinessUser() == isBusinessUser).collect(Collectors.toList());
    }

    @Override
    public List<Computer> searchComputer(String keyword) {
        return computerRepository.searchComputer(keyword);
    }

    @Override
    public Computer findComputerById(Long computerId) throws Exception {
        Optional<Computer> optionalComputer = computerRepository.findById(computerId);

        if (optionalComputer.isEmpty()){
            throw new Exception("Computer Not Exist");
        }
        return optionalComputer.get();
    }

    @Override
    public Computer updateAvailabilityStatus(Long computerId) throws Exception {
        Computer computer = findComputerById(computerId);
        computer.setAvailable(!computer.isAvailable());
        return computerRepository.save(computer);
    }
}
