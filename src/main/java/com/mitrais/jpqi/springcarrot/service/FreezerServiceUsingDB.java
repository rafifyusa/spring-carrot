package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Freezer;
import com.mitrais.jpqi.springcarrot.repository.FreezerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreezerServiceUsingDB {
    @Autowired
    FreezerRepository freezerRepository;

    public FreezerServiceUsingDB(FreezerRepository freezerRepository){
        this.freezerRepository = freezerRepository;
    }

    public List<Freezer> getAll(){
        return freezerRepository.findAll();
    }

    public void create(Freezer freezer){
        freezerRepository.save(freezer);
    }

    public List<Freezer> findAllById(String id){
        return freezerRepository.findAll().stream().filter((f)-> f.getId().equals(id)).collect(Collectors.toList());
    }
}
