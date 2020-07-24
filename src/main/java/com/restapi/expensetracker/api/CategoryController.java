package com.restapi.expensetracker.api;

import com.restapi.expensetracker.domain.Category;
import com.restapi.expensetracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String getAllCategories(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        return "Authenticated! UserId: " + userId;
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(
            HttpServletRequest request,
            @RequestBody Map<String, Object> categoryMap
    ) {
        final int userId = (Integer) request.getAttribute("userId");
        final String title = (String) categoryMap.get("title");
        final String description = (String) categoryMap.get("description");

        Category category = categoryService.addCategory(userId, title, description);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
