package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.Ingredient;
import com.fridgerescuer.springboot.data.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ComponentScan(basePackages = "com.fridgerescuer.springboot")
@DataMongoTest
class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private IngredientDao ingredientDao;
/*
    @Test
    void addIngredientsAndExpirationDateToMemberByIngredientId(){
        //given
        MemberDTO member = MemberDTO.builder().name("야생마").build();

        List<String> ingredientIds = new ArrayList<>();
        ingredientIds.add(ingredientDao.save(IngredientDTO.builder().name("마늘").build()).getId());
        ingredientIds.add(ingredientDao.save(IngredientDTO.builder().name("사과").build()).getId());

        LocalDate now = LocalDate.now();

        List<ExpirationData> expirationDataList = new ArrayList<>();
        ExpirationData garlicData = ExpirationData.builder().ingredientId(ingredientIds.get(0)).expirationDate(now.plusDays(21)).isNoExpiration(false).build();
        ExpirationData appleData = ExpirationData.builder().ingredientId(ingredientIds.get(1)).expirationDate(now.plusDays(14)).isNoExpiration(false).build();
        expirationDataList.add(garlicData);
        expirationDataList.add(appleData);

        //when
        MemberDTO saveMember = memberDao.saveMember(member);
        memberDao.addIngredientAndExpirationDataToMember(saveMember.getId(), ingredientIds, expirationDataList);

        //then
        MemberDTO foundMember = memberDao.findById(saveMember.getId());
        List<ExpirationData> memberExpirationDataList = foundMember.getExpirationDataList();

        for (int i=0; i<expirationDataList.size() ; ++i ){
            assertThat(memberExpirationDataList.get(i).getExpirationDate()).isEqualTo(memberExpirationDataList.get(i).getExpirationDate());
            assertThat(memberExpirationDataList.get(i).getIngredientId()).isEqualTo(memberExpirationDataList.get(i).getIngredientId());
        }
    }
*/
}