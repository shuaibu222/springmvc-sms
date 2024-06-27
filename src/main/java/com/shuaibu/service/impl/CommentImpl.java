package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.CommentDto;
import com.shuaibu.model.CommentModel;
import com.shuaibu.repository.CommentRepository;
import com.shuaibu.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.CommentMapper.*;

@Service
public class CommentImpl implements CommentService {
    
    private CommentRepository commentRepository;

    public CommentImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<CommentModel> comments = commentRepository.findAll();
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long id) {
        return mapToDto(commentRepository.findById(id).get());
    }

    @Override
    public CommentModel saveOrUpdateComment(CommentDto commentDto) {
        return commentRepository.save(mapToModel(commentDto));
    }

    
    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
