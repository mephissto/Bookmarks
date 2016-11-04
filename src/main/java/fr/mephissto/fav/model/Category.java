package fr.mephissto.fav.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private int categoryOrder;

    @ManyToOne
    @JsonBackReference
    private Category parentCategory;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "parentCategory")
    @JsonManagedReference
    private List<Category> children;

    public Category() {}

    public Category(long id, String title, int categoryOrder, Category parentCategory) {
        this.id = id;
        this.title = title;
        this.categoryOrder = categoryOrder;
        this.parentCategory = parentCategory;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }

    public void setBookmarkList(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", categoryOrder=" + categoryOrder +
                ", parentCategory=" + parentCategory.getId() + " - " + parentCategory.getTitle() +
                '}';
    }
}
