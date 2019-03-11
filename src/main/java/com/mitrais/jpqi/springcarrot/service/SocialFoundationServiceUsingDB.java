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
    }

    @Override
    public void deleteSocialFoundation(int id) {

    }

    @Override
    public void updateSocialFoundation(int id, SocialFoundation socialFoundation) {
        socialFoundationRepository.save(socialFoundation);
    }

    @Override
    public List<SocialFoundation> getAllSocialFoundation() {
        return socialFoundationRepository.findAll();
    }

    @Override
    public List<SocialFoundation> getSocialFoundationById(int id) {
        return socialFoundationRepository.findAll().stream().filter((sf)->sf.getId()==id).collect(Collectors.toList());
    }
}
