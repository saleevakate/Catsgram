package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.SortOrder;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "0") int from) {

        SortOrder sortOrder = SortOrder.from(sort);
        if (sortOrder == null) {
            throw new IllegalArgumentException("Недопустимое значение параметра sort: " + sort +
                    ". Допустимые значения: asc, ascending, desc, descending");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Параметр size должен быть больше нуля");
        }
        if (from < 0) {
            throw new IllegalArgumentException("Параметр from не может быть отрицательным");
        }

        return postService.findAll(size, sortOrder, from);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        Post createdPost = postService.create(post);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPost);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }

    @GetMapping("/posts/{postId}")
    public Optional<Post> findById(@PathVariable long postId) {
        return postService.postById(postId);
    }
}