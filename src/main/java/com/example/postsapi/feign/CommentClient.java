package com.example.postsapi.feign;

import com.example.postsapi.bean.CommentBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="comment")
public interface CommentClient {
    @GetMapping(value="/post/{postId}/comment")
    public List<CommentBean> getCommentsByPostId(@PathVariable int postId);

    @DeleteMapping(value="/purge/{postId}")
    public void deleteCommmentsByPostId(@PathVariable int postId);
}
