package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Award;
import com.mitrais.jpqi.springcarrot.repository.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AwardService {
    @Autowired
    private AwardRepository awardRepository;

    public List<Award> findAllAwards() {return awardRepository.findAll();}

    public void createAward (Award award) {awardRepository.save(award);}

    public void updateAward (String id, Award award) {
        award.setId(id);
        awardRepository.save(award);}

    public void deleteAward (String id) { awardRepository.deleteById(id);}

    public List<Award> checkAwardWithTypeDateHappenedToday(){
        LocalDate date = LocalDate.now();
        LocalDate dateNext = date.plusDays(1);
        System.out.println(date);
        return awardRepository.findAwardsByDateEqualsAndActive(date, dateNext,true);
    }
}
