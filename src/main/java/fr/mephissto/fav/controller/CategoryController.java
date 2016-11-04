package fr.mephissto.fav.controller;

import fr.mephissto.fav.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import fr.mephissto.fav.repository.CategoryRepository;

import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @CrossOrigin
    @RequestMapping("/list")
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

}
