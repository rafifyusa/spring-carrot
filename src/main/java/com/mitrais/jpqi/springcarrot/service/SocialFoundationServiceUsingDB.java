package com.mitrais.jpqi.springcarrot.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.model.SocialFoundation;
import com.mitrais.jpqi.springcarrot.repository.SocialFoundationRepository;
import com.mitrais.jpqi.springcarrot.responses.SocialFoundationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SocialFoundationServiceUsingDB implements SocialFoundationService{

    @Autowired
    SocialFoundationRepository socialFoundationRepository;


    // Cloudinary setup
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dc1lp90qy",
            "api_key", "194312298198378",
            "api_secret", "FCxNYbqo0okfaWU_GDPhJdKR0TQ"));

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
        List<SocialFoundation> list = socialFoundationRepository.findAll();
        res.setListSocialFoundation(list);
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

    private String storeImage(String imageString, String id) {
        byte[] imageByteArray = Base64.getDecoder().decode(imageString);
        String url = "";
        try {
            Map uploadResult = cloudinary.uploader().upload(imageByteArray, ObjectUtils.asMap("folder," +
                    "pictures/", "public_id", id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    // Patch Methods
    public void picturePatch(String imageString, String id) {
        // Find Social foundation first
        SocialFoundation socialFoundation = socialFoundationRepository.findById(id).orElse(null);

        if (socialFoundation != null) {
            // Set all field as it is, except picture url field
            socialFoundation.setName(socialFoundation.getName());
            socialFoundation.setStatus(socialFoundation.getStatus());
            socialFoundation.setTotal_carrot(socialFoundation.getTotal_carrot());
            socialFoundation.setMin_carrot(socialFoundation.getMin_carrot());
            socialFoundation.setDescription(socialFoundation.getDescription());
            socialFoundation.setPendingDonations(socialFoundation.getPendingDonations());

            // Set picture url
            String socialFoundationPicture = storeImage(imageString, id);
            socialFoundation.setPictureUrl(socialFoundationPicture);
        }
        socialFoundationRepository.save(socialFoundation);
    }
}
