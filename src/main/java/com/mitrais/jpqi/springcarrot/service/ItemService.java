package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Item;
import com.mitrais.jpqi.springcarrot.repository.ItemRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Create new item
    public void createItem(Item item) {
        itemRepository.save(item);
    }

    // Edit/Update
    public void updateItem(String id, Item item) {
        item.setId(id);
        itemRepository.save(item);
    }

    // Delete
    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }

    // Show All
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item findItemById(String id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        }
        return null;
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
        return itemRepository.findByBazaar( new ObjectId(id));
    }

    public List<Item> findAllByExchangeRateAmount (int emplooyeCarrot) {
        return itemRepository.findAll().stream()
                .filter(item -> item.getExchangeRate() <= emplooyeCarrot)
                .collect(Collectors.toList());
    }
}
