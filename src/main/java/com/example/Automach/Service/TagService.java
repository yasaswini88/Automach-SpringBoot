
package com.example.Automach.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Automach.entity.Tag;
import com.example.Automach.repo.TagRepo;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepo tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
