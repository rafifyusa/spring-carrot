package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.repository.BasketRepository;
import org.springframework.stereotype.Service;

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

    public void updateBasketIntoDB (Basket basket) {
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
}
