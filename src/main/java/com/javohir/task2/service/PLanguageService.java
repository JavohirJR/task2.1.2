package com.javohir.task2.service;

import com.javohir.task2.entity.ProgrammingLanguage;
import com.javohir.task2.payload.ApiResponse;
import com.javohir.task2.repository.PLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PLanguageService {
    @Autowired
    PLRepository plRepository;

    public ApiResponse addOne(ProgrammingLanguage programmingLanguage){
        boolean existsByName = plRepository.existsByName(programmingLanguage.getName());
        if (existsByName) return new ApiResponse("PL is already exist", false);
        ProgrammingLanguage pr = new ProgrammingLanguage();
        pr.setName(programmingLanguage.getName());
        plRepository.save(pr);
        return new ApiResponse("Programming language saved successfully", true);
    }

    public ApiResponse getOne(Integer id){
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = plRepository.findById(id);
        return optionalProgrammingLanguage.map(programmingLanguage -> new ApiResponse("success", true, programmingLanguage)).orElseGet(() -> new ApiResponse("PL is not found", false));
    }

    public ApiResponse deleteOne(Integer id){
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = plRepository.findById(id);
        if (!optionalProgrammingLanguage.isPresent()) return new ApiResponse("PL is not found", false);

        plRepository.deleteById(id);
        return new ApiResponse("deleted successfully", true, optionalProgrammingLanguage.get());
    }

    public List<ProgrammingLanguage> getAll(){
        return plRepository.findAll();
    }

    public ApiResponse edit(Integer id, ProgrammingLanguage programmingLanguage){
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = plRepository.findById(id);
        if (!optionalProgrammingLanguage.isPresent()) return new ApiResponse("PL is not found", false);

        ProgrammingLanguage pr = optionalProgrammingLanguage.get();
        pr.setName(programmingLanguage.getName());
        plRepository.save(pr);

        return new ApiResponse("Successfully edited", true);
    }

}
