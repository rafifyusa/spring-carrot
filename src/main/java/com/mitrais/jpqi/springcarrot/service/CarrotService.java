package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Carrot;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CarrotService {
    List<Carrot> getAllCarrot();
    void createCarrot(Carrot carrot);
    void updateCarrot(Carrot carrot);
    List<Carrot> findByEmployeeId(int id);

}
