package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.repository.BarnRepository;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
import com.mitrais.jpqi.springcarrot.repository.FreezerRepository;
import com.mitrais.jpqi.springcarrot.responses.BarnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarnService {
    private final BarnRepository barnRepository;

    private final CarrotServiceUsingDB carrotServiceUsingDB;

    private final EmployeeService employeeService;

    private final FreezerServiceUsingDB freezerServiceUsingDB;

    private final CarrotRepository carrotRepository;

    private final FreezerRepository freezerRepository;

    @Autowired
    public BarnService(CarrotServiceUsingDB carrotServiceUsingDB, BarnRepository barnRepository,
                       EmployeeService employeeService, FreezerServiceUsingDB freezerServiceUsingDB,
                       CarrotRepository carrotRepository, FreezerRepository freezerRepository) {
        this.carrotServiceUsingDB = carrotServiceUsingDB;
        this.barnRepository = barnRepository;
        this.employeeService = employeeService;
        this.freezerServiceUsingDB = freezerServiceUsingDB;
        this.carrotRepository = carrotRepository;
        this.freezerRepository = freezerRepository;
    }

    public List<Barn> findAllBarn () {return barnRepository.findAll(); }

    public BarnResponse findBarnById (String id) {
        BarnResponse res = new BarnResponse();
        if (barnRepository.findById(id).isPresent()) {
            res.setStatus(true);
            res.setMessage("List found");
            res.setBarn(barnRepository.findById(id).get());
        }
        else {
            res.setStatus(true);
            res.setMessage("Barn not found");
        }
        return res;
    }
    public BarnResponse createBarn (Barn barn) {
        BarnResponse res = new BarnResponse();
        try {
            barnRepository.save(barn);
            res.setStatus(true);
            res.setMessage("Barn successfully inserted");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    //Old Funneling function and might be useless
    /*public void funnelBarn (Barn barn) {
        if (barn.isStatus()) {
            long count = barn.getTotalCarrot();
            String barnId = barn.getId();
            carrotServiceUsingDB.createFrozenCarrotOnBarnCreation(count, barn);

            //Distributing Carrots to Senior managers
            List<Freezer> seniorManagerFreezer = freezerServiceUsingDB.getAll().stream()
                    .filter(f -> f.getEmployee().getRole() == Employee.Role.SENIOR_MANAGER)
                    .collect(Collectors.toList());
            List<Carrot> freshCarrots = carrotServiceUsingDB.findFreshCarrotByBarnId(barnId);
            System.out.println( "fresh carrot = "+ freshCarrots.size());
            long distributeAmt = Math.round(0.75*freshCarrots.size());
            System.out.println( "distribute amt = "+distributeAmt);
            List<Carrot> carrotsToDistribute = freshCarrots.stream().limit(distributeAmt).collect(Collectors.toList());
            System.out.println( "carrotstodistribute = " + carrotsToDistribute.size());

            List<List<Carrot>> chunks = new ArrayList<>();
            long chunkSize = ((int)distributeAmt/seniorManagerFreezer.size());
            for(int i = 0; i < seniorManagerFreezer.size();i++) {
                chunks.add(i,new ArrayList<>());
            }
            chunks.forEach(c -> {
                int i = 0;
                while(i < chunkSize) {
                    if(carrotsToDistribute.isEmpty()){
                        break ;
                    }
                    c.add(carrotsToDistribute.get(0));
                    carrotsToDistribute.remove(0);
                    i++;
                }
            });

            System.out.println( "chunk size = "+ chunks.size());
            for (int i = 0 ; i < chunks.size(); i++){
                Freezer smFreezer = seniorManagerFreezer.get(i);
                chunks.get(i).forEach(carrot ->
                {
                    smFreezer.setCarrot_amt(smFreezer.getCarrot_amt()+1);
                    freezerRepository.save(smFreezer);
                    carrot.setFreezer(smFreezer);
                    carrotRepository.save(carrot);
                });
            }
        }
    }*/

    public BarnResponse deleteBarn (String id) {
        BarnResponse res = new BarnResponse();
        try {
            barnRepository.deleteById(id);
            res.setStatus(true);
            res.setMessage("Barn successfully deleted");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public BarnResponse updateBarn (String id, Barn barn) {
        BarnResponse res = new BarnResponse();
        try {
            barn.setId(id);
            barnRepository.save(barn);
            res.setStatus(true);
            res.setMessage("Barn successfully updated");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public void addAwardsToBarn(String id, List<Award> awards) {
        Barn barn = findBarnById(id).getBarn();

        if (barn.getAwards() == null) {
            barn.setAwards(new ArrayList<>());
        }
        List<Award> awardList = barn.getAwards();
        awardList.addAll(awards);

        barn.setAwards(awardList);
        barnRepository.save(barn);
    }

    public void deleteAwardsFromBarn(String id, Award award){
        Barn barn = findBarnById(id).getBarn();
        if (barn.getAwards() != null) {
            barn.getAwards().remove(award);
        }
        barnRepository.save(barn);

    }
}
