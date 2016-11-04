package fr.mephissto.fav.controller;

import com.google.gson.Gson;
import fr.mephissto.fav.model.Bookmark;
import fr.mephissto.fav.model.Category;
import fr.mephissto.fav.repository.BookmarkRepository;
import fr.mephissto.fav.repository.CategoryRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/app")
public class InitController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @RequestMapping("/init")
    public void init() throws IOException, ParseException {

        Gson gson = new Gson();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // -- Category
        File categoryJsonFile = new File(getClass().getClassLoader().getResource("category.json").getFile());
        String categoryJsonString = FileUtils.readFileToString(categoryJsonFile);

        CategoryJson[] categoriesJson = gson.fromJson(categoryJsonString, CategoryJson[].class);

        // Create all category
        for (CategoryJson categoryJson : categoriesJson) {
            Category category = new Category();
            category.setTitle(categoryJson.getTitle());
            category.setCategoryOrder(categoryJson.getOrder());
            categoryRepository.save(category);
        }

        // Update category with parent now that they exists
        for (CategoryJson categoryJson : categoriesJson) {
            if (categoryJson.getParent_id() != 0l) {
                Category category = categoryRepository.findOne(categoryJson.getId());
                category.setParentCategory(categoryRepository.findOne(categoryJson.getParent_id()));
                categoryRepository.save(category);
            }
        }

        // -- Bookmark
        File bookmarkJsonFile = new File(getClass().getClassLoader().getResource("bookmark.json").getFile());
        String bookmarkJsonString = FileUtils.readFileToString(bookmarkJsonFile);

        BookmarkJson[] bookmarksJson = gson.fromJson(bookmarkJsonString, BookmarkJson[].class);

        for (BookmarkJson bookmarkJson : bookmarksJson) {
            Bookmark bookmark = new Bookmark();
            bookmark.setTitle(bookmarkJson.getTitle());
            bookmark.setUrl(bookmarkJson.getUrl());
            bookmark.setClic(bookmarkJson.getClic());
            bookmark.setDateCreated(null);
            if (!bookmarkJson.getDate_created().equals("0000-00-00 00:00:00")) {
                bookmark.setDateCreated(simpleDateFormat.parse(bookmarkJson.getDate_created()));
            }
            bookmark.setCategory(categoryRepository.findOne(bookmarkJson.getCategory_id()));
            bookmarkRepository.save(bookmark);
        }
    }

    public class CategoryJson {
        private long id;
        private String title;
        private int order;
        private long parent_id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public long getParent_id() {
            return parent_id;
        }

        public void setParent_id(long parent_id) {
            this.parent_id = parent_id;
        }

        public CategoryJson(long id, String title, int order, long parent_id) {
            this.id = id;
            this.title = title;
            this.order = order;
            this.parent_id = parent_id;
        }
    }

    public class BookmarkJson {
        private long id;
        private long category_id;
        private String title;
        private String url;
        private long clic;
        private String date_created;

        public BookmarkJson(long id, long category_id, String title, String url, long clic, String date_created) {
            this.id = id;
            this.category_id = category_id;
            this.title = title;
            this.url = url;
            this.clic = clic;
            this.date_created = date_created;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getCategory_id() {
            return category_id;
        }

        public void setCategory_id(long category_id) {
            this.category_id = category_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getClic() {
            return clic;
        }

        public void setClic(long clic) {
            this.clic = clic;
        }

        public String getDate_created() {
            return date_created;
        }

        public void setDate_created(String date_created) {
            this.date_created = date_created;
        }
    }
}

