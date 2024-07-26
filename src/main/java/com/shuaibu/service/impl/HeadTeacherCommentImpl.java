package com.shuaibu.service.impl;

import com.shuaibu.dto.HeadTeacherCommentDto;
import com.shuaibu.dto.HeadTeacherDto;
import com.shuaibu.mapper.HeadTeacherCommentMapper;
import com.shuaibu.model.HeadTeacherCommentModel;
import com.shuaibu.repository.HeadTeacherCommentRepository;
import com.shuaibu.service.HeadTeacherCommentService;
import com.shuaibu.service.HeadTeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.HeadTeacherCommentMapper.mapToDto;
import static com.shuaibu.mapper.HeadTeacherCommentMapper.mapToModel;

@Service
public class HeadTeacherCommentImpl implements HeadTeacherCommentService {

    private final HeadTeacherCommentRepository headTeacherCommentRepository;
    private final HeadTeacherService headTeacherService;

    public HeadTeacherCommentImpl(HeadTeacherCommentRepository headTeacherCommentRepository, HeadTeacherService headTeacherService) {
        this.headTeacherCommentRepository = headTeacherCommentRepository;
        this.headTeacherService = headTeacherService;
    }

    @Override
    public List<HeadTeacherCommentDto> getAllHeadTeacherComments() {
        List<HeadTeacherCommentModel> headTeacherComments = headTeacherCommentRepository.findAll();
        return headTeacherComments.stream().map(HeadTeacherCommentMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public HeadTeacherCommentDto getHeadTeacherCommentById(Long id) {
        return mapToDto(headTeacherCommentRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveOrUpdateHeadTeacherComment(HeadTeacherCommentDto headTeacherCommentDto) {
        // TODO: handle update
        HeadTeacherDto headTeacherModel = headTeacherService.getHeadTeacherById(Long.valueOf(headTeacherCommentDto.getTeacherId()));

        headTeacherCommentDto.setSectionName(headTeacherModel.getSectionName());
        headTeacherCommentDto.setSectionId(headTeacherModel.getSectionId());
        headTeacherCommentDto.setTeacherName(headTeacherModel.getTeacherName());

        headTeacherCommentRepository.save(mapToModel(headTeacherCommentDto));
    }

    
    @Override
    public void deleteHeadTeacherComment(Long id) {
        headTeacherCommentRepository.deleteById(id);
    }
}
