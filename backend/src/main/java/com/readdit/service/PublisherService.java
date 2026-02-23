package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.model.Publisher;
import com.readdit.repository.PublisherRepository;

@Service
public class PublisherService {
    
    @Autowired
    public PublisherRepository pblshrRepo;

    public Publisher insert(Publisher pblshr) {
        pblshrRepo.insert(pblshr);
        return pblshr;
    }

    public Publisher update(String publisherId, Publisher pblshr) {
        Publisher existing = pblshrRepo.getById(publisherId);

        if (pblshr.getName() != null && !pblshr.getName().isEmpty())
            existing.setName(pblshr.getName());

        pblshrRepo.update(existing);
        return existing;
    }

    public void deleteById(String id) {
        pblshrRepo.deleteById(id);
    }

    public Publisher getById(String id) {
        return pblshrRepo.getById(id);
    }

    public List<Publisher> getAll() {
        return pblshrRepo.getAll();
    }
}
