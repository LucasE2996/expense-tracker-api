package com.restapi.expensetracker.api;

import com.google.common.collect.ImmutableMap;
import com.restapi.expensetracker.Constants;
import com.restapi.expensetracker.domain.Category;
import com.restapi.expensetracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        final int userId = (Integer) request.getAttribute(Constants.USER_ID);
        final List<Category> categories = categoryService.fetchAllCategories(userId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId
    ) {
        final int userId = (Integer) request.getAttribute(Constants.USER_ID);
        final Category category = categoryService.fetchCategoryById(userId, categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(
            HttpServletRequest request,
            @RequestBody Map<String, Object> body
    ) {
        final int userId = (Integer) request.getAttribute(Constants.USER_ID);
        final String title = (String) body.get("title");
        final String description = (String) body.get("description");

        final Category category = categoryService.addCategory(userId, title, description);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PostMapping("/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> updateCategory(
            HttpServletRequest resquest,
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody Category category
    ) {
        final int userId = (Integer) resquest.getAttribute("userId");
        categoryService.updateCategory(userId, categoryId, category);
        return new ResponseEntity<>(ImmutableMap.of("success", true), HttpStatus.OK);
    }
}
