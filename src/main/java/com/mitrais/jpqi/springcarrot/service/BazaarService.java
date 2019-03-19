package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Bazaar;
import com.mitrais.jpqi.springcarrot.repository.BazaarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BazaarService {

    private BazaarRepository bazaarRepository;

    // Constructor
    public BazaarService(BazaarRepository bazaarRepository) {
        this.bazaarRepository = bazaarRepository;
    }

    // Create
    public void createBazaar(Bazaar bazaar) {
        bazaarRepository.save(bazaar);
    }

    // Update
    public void updateBazaar(String id, Bazaar bazaar) {
        bazaar.setId(id);
        bazaarRepository.save(bazaar);
    }

    // Show all Bazaar
    public List<Bazaar> findAllBazaar() {
        return bazaarRepository.findAll();
    }

    // Find Bazaar by Id
    public Bazaar findById(String id) {
        Optional<Bazaar> temp = bazaarRepository.findById(id);
        if (temp.isPresent()) {
            return temp.get();
        }
        return null;
    }

    public List<Bazaar> findByStatus(boolean status) {
        return bazaarRepository.findBazaarByStatus(status);
    }

    // Change bazaar status (activate or deactivate)
    public void changeBazaarStatus(String id) {
        // Create temporary bazaar
        Bazaar temp = bazaarRepository.findById(id).orElse(null);

        if(temp != null) {
            // from bazaar, set another field same as it is
            temp.setId(temp.getId());
            temp.setBazaarName(temp.getBazaarName());
            temp.setStartPeriod(temp.getStartPeriod());
            temp.setEndPeriod(temp.getEndPeriod());

            temp.setStatus(!temp.isStatus());
        }
        bazaarRepository.save(temp);
    }

}
