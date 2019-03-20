package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Carrot> findByBasketId(String id) {
        return carrotRepository.findByBasketId(new ObjectId(id));
    }

    public int countMyCarrotSum (String id) {
        List<Carrot> carrots = carrotRepository.findByBasketId(new ObjectId(id));
        return carrots.size();
    }

    public List<Carrot> findUsableCarrotByBasketId(String id) {
        List<Carrot> usableCarrots = carrotRepository.findByBasketId(new ObjectId(id))
                .stream().filter(e -> e.isUsable())
                .collect(Collectors.toList());
        return usableCarrots;
    }

    public List<Carrot> findUsableCarrotByFreezerId(String id) {
        List<Carrot> usableCarrots = carrotRepository.findByFreezerId(new ObjectId(id))
                .stream().filter(e-> e.isUsable())
                .collect(Collectors.toList());
        return usableCarrots;
    }
}
