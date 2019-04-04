package com.mitrais.jpqi.springcarrot.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mitrais.jpqi.springcarrot.model.Bazaar;
import com.mitrais.jpqi.springcarrot.model.Item;
import com.mitrais.jpqi.springcarrot.repository.ItemRepository;
import com.mitrais.jpqi.springcarrot.responses.ItemResponse;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dc1lp90qy",
            "api_key", "194312298198378",
            "api_secret", "FCxNYbqo0okfaWU_GDPhJdKR0TQ"));

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Create new item
    public Map<String, String> createItem(Item item) {
        Map<String, String> hasil = new HashMap<>();
        hasil.put("id", itemRepository.save(item).getId());
        return hasil;
    }

    // Edit/Update
    public void updateItem(String id, Item item) {
        item.setId(id);
        itemRepository.save(item);
    }

    // Delete
    public ItemResponse deleteItem(String id) {
        ItemResponse res = new ItemResponse();
        try {
            itemRepository.deleteById(id);
            res.setStatus(true);
            res.setMessage("item successfully deleted");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    // Show All
    public ItemResponse getAll() {
        ItemResponse res = new ItemResponse();
        res.setStatus(true);
        res.setMessage("List Item");
        res.setListItem(itemRepository.findAll());
        return res;
    }

    public ItemResponse findItemById(String id) {
        Optional<Item> item = itemRepository.findById(id);
        ItemResponse res = new ItemResponse();
        if (item.isPresent()) {
            res.setStatus(true);
            res.setMessage("Item Found");
            res.setItem(item.get());
        } else {
            res.setStatus(false);
            res.setMessage("Item not Found");
        }
        return res;
    }

    // Patch
    public void updatePartialItem(String id, Item item) {
        // Create item object
        Item temp = itemRepository.findById(id).orElse(null);

        // Check if empty or not
        if (temp != null) {
            // Fixed id
            temp.setId(temp.getId());

            if (temp.getItemName() != null) {
                temp.setItemName(item.getItemName());
            }

            if (temp.getItemDescription() != null) {
                temp.setItemDescription(item.getItemDescription());
            }

            if (temp.getPictureUrl() != null) {
                temp.setPictureUrl(item.getPictureUrl());
            }

            if (temp.getExchangeRate() != 0) {
                temp.setExchangeRate(item.getExchangeRate());
            }

            if (temp.getTotalItem() != 0) {
                temp.setTotalItem(item.getTotalItem());
            }

            if (temp.isApprovalStatus() || !temp.isApprovalStatus()) {
                temp.setApprovalStatus(item.isApprovalStatus());
            }

            if (temp.isSaleStatus() || !temp.isSaleStatus()) {
                temp.setSaleStatus(item.isSaleStatus());
            }

            if (temp.getItemSold() != 0 ) {
                temp.setItemSold(item.getItemSold());
            }

            if (temp.getBazaar() != null) {
                temp.setBazaar(item.getBazaar());
            }
        }
        itemRepository.save(temp);
    }

    // Sort by exchange rate
    public List<Item> sortByExchangeRate() {
//        List<Item> sortedItem = itemRepository.findAll(new Sort(Sort.Direction.DESC, "exchangeRate"));
//        return sortedItem;
        return itemRepository.findAll(new Sort(Sort.Direction.DESC, "exchangeRate"));
    }

    public List<Item> findAllByBazaarId(String id) {
//        return itemRepository.findByBazaar( new ObjectId(id));
        List<Item> mm = itemRepository.findByBazaar( new ObjectId(id));
        return mm;
    }

    public List<Item> findAllByMultipleBazaarId(List<Bazaar> id) {
        ObjectId[] a = new ObjectId[id.size()];
        final int[] i = {0};
        id.forEach(e -> {
            System.out.println(e.getId());
            a[i[0]] = new ObjectId(e.getId());
            i[0]++;
        });

        List<Item> mm = itemRepository.findByMultipleBazaar(a);
        return mm;
    }
    public List<Item> findAllByExchangeRateAmount (int emplooyeCarrot) {
        return itemRepository.findAll().stream()
                .filter(item -> item.getExchangeRate() <= emplooyeCarrot)
                .collect(Collectors.toList());
    }

    // Upload image for item
    public String storeImage(String imageString, String id) {
        byte[] imageByteArray = Base64.getDecoder().decode(imageString);
        String url = "";
        try {
            Map uploadResult = cloudinary.uploader().upload(imageByteArray, ObjectUtils.asMap("folder", "pictures/",
                    "public_id", id));
            url = (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  url;
    }

    // Patch Upload Image for Item
    public void picturePatch (String imageString, String id) {
//        // Find item
        Item item = itemRepository.findById(id).orElse(null);
//
        if (item != null) {
            // Set all field as it's except pictureUrl field
            item.setItemName(item.getItemName());
            item.setItemDescription(item.getItemDescription());
            item.setExchangeRate(item.getExchangeRate());
            item.setTotalItem(item.getTotalItem());
            item.setApprovalStatus(item.isApprovalStatus());
            item.setSaleStatus(item.isSaleStatus());
            item.setItemSold(item.getItemSold());
            item.setBazaar(item.getBazaar());

            // Change picture url field;
            String itemPictureName = storeImage(imageString, id);
            item.setPictureUrl(itemPictureName);
        }
        itemRepository.save(item);
    }
}
