package com.example.postsapi.repository;

import com.example.postsapi.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PostRepository extends CrudRepository <Post, Integer> {
    @Query("FROM Post p WHERE p.user_id=?1")
    public Iterable<Post> findByUserId(int userId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO posts (title, description, user_id) VALUES (:postTitle, :postDescription, :userId)", nativeQuery = true)
    public void createPost(String postTitle, String postDescription, int userId);
}
