package com.readdit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readdit.dto.request.PublisherRequest;
import com.readdit.exception.ResourceAlreadyExistsException;
import com.readdit.exception.ResourceNotFoundException;
import com.readdit.model.Publisher;
import org.springframework.transaction.annotation.Transactional;

import com.readdit.repository.PublisherRepository;
import com.readdit.util.RegexHelper;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository pblshrRepo;

    public Publisher create(PublisherRequest req) {
        if (pblshrRepo.getById(RegexHelper.toSlug(req.getName())) != null) {
            throw new ResourceAlreadyExistsException("Publisher with name " + req.getName() + " already exists");
        }
        Publisher pblshr = req.toPublisher();
        pblshr.setId(RegexHelper.toSlug(req.getName()));
        return pblshrRepo.insert(pblshr);
    }

    @Transactional
    public Publisher update(String publisherId, PublisherRequest req) {
        Publisher existing = pblshrRepo.getById(publisherId);
        if (existing == null) {
            throw new ResourceNotFoundException("Publisher with id " + publisherId + " not found");
        }
        if (req.getName() != null && !req.getName().isEmpty())
            existing.setName(req.getName());
            existing.setId(RegexHelper.toSlug(req.getName()));
        pblshrRepo.update(existing);
        return existing;
    }

    @Transactional
    public void deleteById(String id) {
        if (pblshrRepo.getById(id) == null) {
            throw new ResourceNotFoundException("Publisher with id " + id + " not found");
        }
        pblshrRepo.deleteById(id);
    }

    public Publisher getById(String id) {
        Publisher publisher = pblshrRepo.getById(id);
        if (publisher == null) {
            throw new ResourceNotFoundException("Publisher with id " + id + " not found");
        }
        return publisher;
    }

    public List<Publisher> getAll() {
        return pblshrRepo.getAll();
    }
}
