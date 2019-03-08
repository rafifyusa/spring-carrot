package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Carrot> getAll(){
        return carrotRepository.findAll();
    }

    @Override
    public void create(Carrot carrot) {
        carrotRepository.save(carrot);
    }
}
