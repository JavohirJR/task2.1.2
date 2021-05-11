package com.javohir.task2.service;

import com.javohir.task2.entity.Category;
import com.javohir.task2.entity.ProgrammingLanguage;
import com.javohir.task2.entity.StarBadge;
import com.javohir.task2.entity.Task;
import com.javohir.task2.entity.enums.StarBadgeValue;
import com.javohir.task2.payload.ApiResponse;
import com.javohir.task2.payload.StarBadgeDTO;
import com.javohir.task2.payload.TaskDTO;
import com.javohir.task2.repository.CategoryRepository;
import com.javohir.task2.repository.PLRepository;
import com.javohir.task2.repository.StarBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StarBadgeService {

    @Autowired
    StarBadgeRepository starBadgeRepository;

    @Autowired
    PLRepository plRepository;

    public ApiResponse addOne(StarBadgeDTO starBadgeDTO){
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = plRepository.findById(starBadgeDTO.getProgrammingLangId());
        if (!optionalProgrammingLanguage.isPresent()) return new ApiResponse("PR is not found", false);

        StarBadge starBadge = new StarBadge();
        starBadge.setValue(starBadgeDTO.getStarBadgeValue());
        starBadge.setLanguage(optionalProgrammingLanguage.get());

        starBadgeRepository.save(starBadge);
        return new ApiResponse("Task is added", true);
    }

    public ApiResponse getOne(Integer id){
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(id);
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Star Badge is not found", false);

        return new ApiResponse("Success", true, optionalStarBadge.get());
    }

    public ApiResponse deleteOne(Integer id){
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(id);
        if (!optionalStarBadge.isPresent()) {
            return new ApiResponse("Star badge is not found", false);
        }
        starBadgeRepository.deleteById(id);
        return new ApiResponse("Success deleted", true, optionalStarBadge.get());

    }

    public ApiResponse editOne(Integer id, StarBadgeDTO starBadgeDTO){
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(id);
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Star badge is not found", false);

        Optional<ProgrammingLanguage> optionalProgrammingLanguage = plRepository.findById(starBadgeDTO.getProgrammingLangId());
        if (!optionalProgrammingLanguage.isPresent()) return new ApiResponse("PR is not found", false);

        StarBadge starBadge = new StarBadge();
        starBadge.setValue(starBadgeDTO.getStarBadgeValue());
        starBadge.setLanguage(optionalProgrammingLanguage.get());

        starBadgeRepository.save(starBadge);
        return new ApiResponse("Star badge edited successfully", true);
    }

    public List<StarBadge> getAll(){
        return starBadgeRepository.findAll();
    }

}
