package com.ads.sgcm_api.domain.service;

import com.ads.sgcm_api.domain.model.Item;
import com.ads.sgcm_api.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Item salvar(Item item){
        return itemRepository.save(item);
    }
}
