package com.allenanker.flashnews.service;

import com.allenanker.flashnews.dao.CommentDao;
import com.allenanker.flashnews.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDao.selectByEntity(entityId, entityType);
    }

    public int getCommentsCount(int entityId, int entityType) {
        return commentDao.getCommentsCount(entityId, entityType);
    }

    public void deleteComment(int commentId) {
        commentDao.updateCommentStatus(commentId);
    }
}
