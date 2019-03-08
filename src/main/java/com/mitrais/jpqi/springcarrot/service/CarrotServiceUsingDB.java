package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarrotServiceUsingDB implements CarrotService {
    @Autowired
    CarrotRepository carrotRepository;

    public CarrotServiceUsingDB(CarrotRepository carrotRepository) {
        this.carrotRepository = carrotRepository;
    }

    @Override
    public List<Carrot> getAllCarrot(){
        return carrotRepository.findAll();
    }

    @Override
    public void createCarrot(Carrot carrot) {
        carrotRepository.save(carrot);
    }

    @Override
    public void updateCarrot(Carrot carrot) { carrotRepository.save(carrot);}
}
