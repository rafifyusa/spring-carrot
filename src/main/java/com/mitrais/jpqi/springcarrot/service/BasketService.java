package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.BasketRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    private BasketRepository basketRepository;


    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public void insertBasketIntoDB (Basket basket) {
         basketRepository.save(basket);
     }

    public void updateBasketIntoDB (int id, Basket basket) {
        basketRepository.save(basket);
    }

    public List<Basket> findAllBasket () {
        return basketRepository.findAll();
    }

    public Basket findBasketById (int id) {
        Optional<Basket> basket = basketRepository.findById(id);
        if (basket.isPresent()) {
            return basket.get();
        }
        return null;
    }

    public void deleteBasketById (int id) {
        basketRepository.deleteById(id);
    }

    public List<Employee> findEmployeeSortedByCarrotAmt(){
        List<Basket> sortedBasketByCarrotAmt = basketRepository.findAll(new Sort(Sort.Direction.DESC, "carrot_amt"));
        List<Employee> sortedEmployee = new ArrayList<>();

        sortedBasketByCarrotAmt.forEach(basket -> {
            sortedEmployee.add(basket.getEmployee());
        });

        return sortedEmployee;
    }

    public List<Basket> findByEmployee(int id) {
        return basketRepository.findByEmployee(id);
    }
}
