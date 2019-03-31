package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Award;
import com.mitrais.jpqi.springcarrot.model.Barn;
import com.mitrais.jpqi.springcarrot.repository.BarnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BarnService {
    private final BarnRepository barnRepository;

    private final CarrotServiceUsingDB carrotServiceUsingDB;

    @Autowired
    public BarnService(CarrotServiceUsingDB carrotServiceUsingDB, BarnRepository barnRepository) {
        this.carrotServiceUsingDB = carrotServiceUsingDB;
        this.barnRepository = barnRepository;
    }

    public List<Barn> findAllBarn () {return barnRepository.findAll(); }

    public Barn findBarnById (String id) {
        if (barnRepository.findById(id).isPresent()) {
            return barnRepository.findById(id).get();
        }
        else {return null;}
    }
    public void createBarn (Barn barn) {
        if (barn.isStatus()) {
            long count = barn.getTotalCarrot();
            String barnId = barn.getId();
            carrotServiceUsingDB.createFrozenCarrotOnBarnCreation(count, barnId);
        }
        barnRepository.save(barn);
    }

    public void deleteBarn (String id) { barnRepository.deleteById(id);}

    public void updateBarn (String id, Barn barn) {
        barn.setId(id);
        barnRepository.save(barn);
    }

    public void addAwardsToBarn(String id, List<Award> awards) {
        Barn barn = findBarnById(id);

        if (barn.getAwards() == null) {
            barn.setAwards(new ArrayList<>());
        }
        List<Award> awardList = barn.getAwards();
        awardList.addAll(awards);

        barn.setAwards(awardList);
        barnRepository.save(barn);
    }

    public void deleteAwardsFromBarn(String id, Award award){
        Barn barn = findBarnById(id);
        if (barn.getAwards() != null) {
            barn.getAwards().remove(award);
        }
        barnRepository.save(barn);

    }
}
