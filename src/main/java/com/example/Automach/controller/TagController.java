//package com.example.Automach.controller;
//
//public class TagController {
//}
package com.example.Automach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.Automach.entity.Tag;
import com.example.Automach.Service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.saveTag(tag);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
