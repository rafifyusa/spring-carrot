package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.SocialFoundation;
import com.mitrais.jpqi.springcarrot.repository.SocialFoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialFoundationServiceUsingDB implements SocialFoundationService{

    @Autowired
    SocialFoundationRepository socialFoundationRepository;

    @Override
    public void createSocialFoundation(SocialFoundation socialFoundation) {
        socialFoundationRepository.save(socialFoundation);
    }

    @Override
    public void deleteSocialFoundation(String id) {
        socialFoundationRepository.deleteById(id);
    }

    @Override
    public void updateSocialFoundation(String id, SocialFoundation socialFoundation) {
        socialFoundation.setId(id);
        socialFoundationRepository.save(socialFoundation);
    }

    @Override
    public List<SocialFoundation> getAllSocialFoundation() {
        return socialFoundationRepository.findAll();
    }

    @Override
    public List<SocialFoundation> getSocialFoundationById(String id) {
        return socialFoundationRepository.findAll().stream().filter((sf)->sf.getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public void partialUpdate(String id, SocialFoundation socialFoundation) {
        SocialFoundation sf = socialFoundationRepository.findById(id).orElse(null);
        if (sf != null) {
            if (socialFoundation.getId() != null) {
                sf.setId(socialFoundation.getId());
            }
            if (socialFoundation.getName() != null) {
                sf.setName(socialFoundation.getName());
            }
            if (socialFoundation.getStatus() != null) {
                sf.setStatus(socialFoundation.getStatus());
            }
            if (socialFoundation.getTotal_carrot() != 0) {
                sf.setTotal_carrot(socialFoundation.getTotal_carrot());
            }
            if (socialFoundation.getMin_carrot() != 0) {
                sf.setMin_carrot(socialFoundation.getMin_carrot());
            }
            if (socialFoundation.getDescription() != null) {
                sf.setDescription(socialFoundation.getDescription());
            }
        }
        socialFoundationRepository.save(sf);
    }
}
