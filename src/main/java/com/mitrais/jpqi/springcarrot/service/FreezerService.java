package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Freezer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FreezerService {
    void deleteFreezer(String id);
    void updateFreezer(String id, String name, Employee employee);
    List<Freezer> create (Freezer freezer);
    List<Freezer> fetch();
    List<Freezer> delete(String id);
    List<Freezer> update(String id, Freezer freezer);
    List<Freezer> findAllByName(String name);
    List<Freezer> findAllById(String id);
}
