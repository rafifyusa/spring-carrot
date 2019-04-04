package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.responses.CarrotResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CarrotService {
    List<Carrot> getAllCarrot();
    CarrotResponse createCarrot(Carrot carrot);
    void updateCarrot(Carrot carrot);
    List<Carrot> findByBasketId(String id);

}
