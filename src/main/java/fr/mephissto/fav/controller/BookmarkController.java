package fr.mephissto.fav.controller;

import fr.mephissto.fav.model.Bookmark;
import fr.mephissto.fav.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @RequestMapping("/list")
    public Iterable<Bookmark> listBookmark() {
        return bookmarkRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Bookmark getBookmark(@PathVariable Long id) {
        return bookmarkRepository.findOne(id);
    }

}
