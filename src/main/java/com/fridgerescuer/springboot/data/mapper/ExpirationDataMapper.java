package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.PrivateExpirationData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( uses = IngredientMapper.class)
public interface ExpirationDataMapper {

    ExpirationDataMapper INSTANCE = Mappers.getMapper(ExpirationDataMapper.class);

    @Mapping(source = "ingredientDTO", target = "ingredient")
    ExpirationData DTOtoData(ExpirationDataDTO expirationDataDTO);
    List<ExpirationData> DTOListToDataList(List<ExpirationDataDTO> expirationDataDTOList);

    @Mapping(source = "ingredientDTO", target = "ingredient")
    PrivateExpirationData DTOtoPrivateData(ExpirationDataDTO expirationDataDTO);
    List<PrivateExpirationData> DTOListToPrivateDataList(List<ExpirationDataDTO> expirationDataDTOList);

    @Mapping(source = "ingredient", target = "ingredientDTO")
    ExpirationDataDTO dataToDTO(ExpirationData expirationData);
    List<ExpirationDataDTO> dataListToDTOList(List<ExpirationData> expirationDataList);
}
