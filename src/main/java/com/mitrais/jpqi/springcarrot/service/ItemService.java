package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Item;
import com.mitrais.jpqi.springcarrot.repository.ItemRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Create new reward item
    public void insertItem(Item item) {
        itemRepository.save(item);
    }

    // Update
    public void updateItem(Item item) {
        itemRepository.save(item);
    }

    // Find All
    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    // Find By Id
    public Item findItemById(int id) {
        Optional<Item> item = itemRepository.findById(id);
        if(!item.isPresent()) {
            return null;
        }
        return item.get();
    }

    // Delete Item
    public void deleteItemById(int id) {
        itemRepository.deleteById(id);
    }

    // Sorting based on amount
    public List<Item> sortByAmount() {
        List<Item> sortedItem = itemRepository.findAll(new Sort(Sort.Direction.DESC, "amount"));
        return sortedItem;
    }
}
