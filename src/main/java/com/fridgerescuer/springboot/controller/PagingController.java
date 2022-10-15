package com.fridgerescuer.springboot.controller;

import com.fridgerescuer.springboot.data.dao.RecipeDao;
import com.fridgerescuer.springboot.data.dto.RecipeDTO;
import com.fridgerescuer.springboot.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PagingController {     //그냥 page 테스트용

    //일단 시범으로 만든거라 dao 접근해서 함
    @Autowired
    private final RecipeDao recipeDao;

    //반환하는 객체가 page 객체로, 총 몇 page 까지 있는지 담겨 있음
    //프런트가 page 1~ end 까지 호출하면 size 만큼 담겨서 전달 됨
    @GetMapping("/recipeListDemo")
    public Page<RecipeDTO> getRecipePageList(
            @RequestParam String name,
            @PageableDefault(size = 5, sort="id",direction = Sort.Direction.DESC) Pageable pageable) {

        List<RecipeDTO> recipeDTOList = recipeDao.findAllByContainName(name);

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), recipeDTOList.size());
        final Page<RecipeDTO> page = new PageImpl<>(recipeDTOList.subList(start, end), pageable, recipeDTOList.size());

        return page;
    }

}
