package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.SocialFoundation;
import com.mitrais.jpqi.springcarrot.repository.SocialFoundationRepository;
import com.mitrais.jpqi.springcarrot.responses.SocialFoundationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialFoundationServiceUsingDB implements SocialFoundationService{

    @Autowired
    SocialFoundationRepository socialFoundationRepository;

    // Constructor
    public SocialFoundationServiceUsingDB(SocialFoundationRepository socialFoundationRepository) {
        this.socialFoundationRepository = socialFoundationRepository;
    }

    @Override
    public SocialFoundationResponse createSocialFoundation(SocialFoundation socialFoundation) {
        SocialFoundationResponse res = new SocialFoundationResponse();
        try {
            socialFoundationRepository.save(socialFoundation);
            res.setStatus(true);
            res.setMessage("social foundation successfully added");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Override
    public SocialFoundationResponse deleteSocialFoundation(String id) {
        SocialFoundationResponse res = new SocialFoundationResponse();
        try {
            socialFoundationRepository.deleteById(id);
            res.setStatus(true);
            res.setMessage("social foundation successfully deleted");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Override
    public SocialFoundationResponse updateSocialFoundation(String id, SocialFoundation socialFoundation) {
        SocialFoundationResponse res = new SocialFoundationResponse();
        try {
            socialFoundation.setId(id);
            socialFoundationRepository.save(socialFoundation);
            res.setStatus(true);
            res.setMessage("social foundation successfully updated");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Override
    public SocialFoundationResponse getAllSocialFoundation() {
        SocialFoundationResponse res = new SocialFoundationResponse();
        res.setStatus(true);
        res.setMessage("List Social Foundation");
        res.setListSocialFoundation(socialFoundationRepository.findAll());
        return res;
    }

    @Override
    public SocialFoundationResponse getSocialFoundationById(String id) {
        SocialFoundationResponse res = new SocialFoundationResponse();
        Optional<SocialFoundation> sf = socialFoundationRepository.findById(id);
        if (sf.isPresent()) {
            res.setStatus(true);
            res.setMessage("Social Foundation Found");
            res.setSocialFoundation(sf.get());
        } else {
            res.setStatus(false);
            res.setMessage("Social Foundation not found");
        }
        return res;
    }

    @Override
    public SocialFoundationResponse partialUpdate(String id, SocialFoundation socialFoundation) {
        SocialFoundationResponse res = new SocialFoundationResponse();
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

        try {
            socialFoundationRepository.save(sf);
            res.setStatus(true);
            res.setMessage("social foundation successfully updated");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    /**
     * Ordered Social Foundation based on mostly contributed
     *
     */
    public List<SocialFoundation> getMostContributedSocialFoundation() {
        return socialFoundationRepository.findAll().stream()
//                .sorted(Comparator.comparing
                .sorted((f1, f2) -> Double.compare(f2.getTotal_carrot(), f1.getTotal_carrot()))
                .collect(Collectors.toList());
    }
}
