package fr.mephissto.fav.repository;

import fr.mephissto.fav.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByTitle(String title);

}
