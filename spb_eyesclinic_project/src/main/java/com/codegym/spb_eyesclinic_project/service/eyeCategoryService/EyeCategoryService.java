package com.codegym.spb_eyesclinic_project.service.eyeCategoryService;

import com.codegym.spb_eyesclinic_project.domain.EyeCategory;
import com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO.EyeCategoryRequest;
import com.codegym.spb_eyesclinic_project.repository.EyeCategoryRepository;
import com.codegym.spb_eyesclinic_project.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EyeCategoryService {
    private final EyeCategoryRepository eyeCategoryRepository;

    public List<EyeCategory> getAll(){
        List<EyeCategory> eyeCategoryList = eyeCategoryRepository.findAll();
        return eyeCategoryList;
    }
    public void create(EyeCategoryRequest request){
        var result = AppUtils.mapper.map(request, EyeCategory.class);
        result.setPrice(new BigDecimal(request.getPrice()));
        eyeCategoryRepository.save(result);
    }

    public EyeCategory findById(Long id){
        Optional<EyeCategory> eyeCategoryOptional = eyeCategoryRepository.findById(id);
        if(eyeCategoryOptional.isPresent()){
            return eyeCategoryOptional.get();
        } else {
            return null;
        }
    }
    public void update(EyeCategoryRequest request, Long id){
        Optional<EyeCategory> eyeCategoryOptional = eyeCategoryRepository.findById(id);
        if(eyeCategoryOptional.isPresent()){
            var result = eyeCategoryOptional.get();
            AppUtils.mapper.map(request,result);
            result.setPrice(new BigDecimal(request.getPrice()));
            eyeCategoryRepository.save(result);
        }
    }

    public void delete(Long id){
        eyeCategoryRepository.deleteById(id);
    }
}
