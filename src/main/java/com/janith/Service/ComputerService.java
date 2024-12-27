package com.janith.Service;

import com.janith.model.Category;
import com.janith.model.Computer;
import com.janith.model.Shop;
import com.janith.request.CreateComputerRequest;

import java.util.List;

public interface ComputerService {
    public Computer createComputer(CreateComputerRequest req, Category category, Shop shop);

    void deleteComputer(Long computerId) throws Exception;

    public List<Computer> getShopComputers(Long shopId,
                                           boolean isGamer,
                                           boolean isHomeUser,
                                           boolean isBusinessUser,
                                           boolean isDesigner,
                                           boolean isDeveloper,
                                           boolean isSeasonal,
                                           String computerCategory
    );

    public List<Computer> searchComputer(String keyword);
    public Computer findComputerById(Long computerId) throws Exception;
    public Computer updateAvailabilityStatus(Long computerId) throws Exception;
}
