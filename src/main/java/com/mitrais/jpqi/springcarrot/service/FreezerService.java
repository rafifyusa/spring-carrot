package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Freezer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FreezerService {
    void deleteFreezer(int id);
    void updateFreezer(int id, String name, Employee employee);
    List<Freezer> create (Freezer freezer);
    List<Freezer> fetch();
    List<Freezer> delete(int id);
    List<Freezer> update(int id, Freezer freezer);
    List<Freezer> findAllByName(String name);
    List<Freezer> findAllById(int id);
}
