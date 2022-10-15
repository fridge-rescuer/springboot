package com.fridgerescuer.springboot.data.mapper;

import com.fridgerescuer.springboot.data.dto.ExpirationDataDTO;
import com.fridgerescuer.springboot.data.dto.IngredientDTO;
import com.fridgerescuer.springboot.data.entity.ExpirationData;
import com.fridgerescuer.springboot.data.entity.PrivateExpirationData;
import com.fridgerescuer.springboot.data.vo.ExpirationDataVO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-10T23:40:39+0900",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.1 (Oracle Corporation)"
)
public class ExpirationDataMapperImpl implements ExpirationDataMapper {

    private final IngredientMapper ingredientMapper = IngredientMapper.INSTANCE;

    @Override
    public ExpirationData DTOtoData(ExpirationDataDTO expirationDataDTO) {
        if ( expirationDataDTO == null ) {
            return null;
        }

        ExpirationData.ExpirationDataBuilder expirationData = ExpirationData.builder();

        expirationData.ingredient( ingredientMapper.DTOtoIngredient( expirationDataDTO.getIngredientDTO() ) );
        expirationData.id( expirationDataDTO.getId() );
        expirationData.expirationDate( expirationDataDTO.getExpirationDate() );
        expirationData.isNoExpiration( expirationDataDTO.getIsNoExpiration() );

        return expirationData.build();
    }

    @Override
    public List<ExpirationData> DTOListToDataList(List<ExpirationDataDTO> expirationDataDTOList) {
        if ( expirationDataDTOList == null ) {
            return null;
        }

        List<ExpirationData> list = new ArrayList<ExpirationData>( expirationDataDTOList.size() );
        for ( ExpirationDataDTO expirationDataDTO : expirationDataDTOList ) {
            list.add( DTOtoData( expirationDataDTO ) );
        }

        return list;
    }

    @Override
    public PrivateExpirationData DTOtoPrivateData(ExpirationDataDTO expirationDataDTO) {
        if ( expirationDataDTO == null ) {
            return null;
        }

        PrivateExpirationData.PrivateExpirationDataBuilder privateExpirationData = PrivateExpirationData.builder();

        privateExpirationData.ingredient( ingredientMapper.DTOtoIngredient( expirationDataDTO.getIngredientDTO() ) );
        privateExpirationData.id( expirationDataDTO.getId() );
        privateExpirationData.expirationDate( expirationDataDTO.getExpirationDate() );
        privateExpirationData.isNoExpiration( expirationDataDTO.getIsNoExpiration() );

        return privateExpirationData.build();
    }

    @Override
    public List<PrivateExpirationData> DTOListToPrivateDataList(List<ExpirationDataDTO> expirationDataDTOList) {
        if ( expirationDataDTOList == null ) {
            return null;
        }

        List<PrivateExpirationData> list = new ArrayList<PrivateExpirationData>( expirationDataDTOList.size() );
        for ( ExpirationDataDTO expirationDataDTO : expirationDataDTOList ) {
            list.add( DTOtoPrivateData( expirationDataDTO ) );
        }

        return list;
    }

    @Override
    public ExpirationDataDTO dataToDTO(ExpirationData expirationData) {
        if ( expirationData == null ) {
            return null;
        }

        ExpirationDataDTO.ExpirationDataDTOBuilder expirationDataDTO = ExpirationDataDTO.builder();

        expirationDataDTO.ingredientDTO( ingredientMapper.ingredientToDTO( expirationData.getIngredient() ) );
        expirationDataDTO.id( expirationData.getId() );
        expirationDataDTO.expirationDate( expirationData.getExpirationDate() );
        expirationDataDTO.isNoExpiration( expirationData.getIsNoExpiration() );

        return expirationDataDTO.build();
    }

    @Override
    public List<ExpirationDataDTO> dataListToDTOList(List<ExpirationData> expirationDataList) {
        if ( expirationDataList == null ) {
            return null;
        }

        List<ExpirationDataDTO> list = new ArrayList<ExpirationDataDTO>( expirationDataList.size() );
        for ( ExpirationData expirationData : expirationDataList ) {
            list.add( dataToDTO( expirationData ) );
        }

        return list;
    }

    @Override
    public ExpirationDataDTO privateDataToDTO(PrivateExpirationData privateExpirationData) {
        if ( privateExpirationData == null ) {
            return null;
        }

        ExpirationDataDTO.ExpirationDataDTOBuilder expirationDataDTO = ExpirationDataDTO.builder();

        expirationDataDTO.ingredientDTO( ingredientMapper.ingredientToDTO( privateExpirationData.getIngredient() ) );
        expirationDataDTO.id( privateExpirationData.getId() );
        expirationDataDTO.expirationDate( privateExpirationData.getExpirationDate() );
        expirationDataDTO.isNoExpiration( privateExpirationData.getIsNoExpiration() );

        return expirationDataDTO.build();
    }

    @Override
    public List<ExpirationDataDTO> privateDataListToDTOList(List<PrivateExpirationData> privateExpirationDataList) {
        if ( privateExpirationDataList == null ) {
            return null;
        }

        List<ExpirationDataDTO> list = new ArrayList<ExpirationDataDTO>( privateExpirationDataList.size() );
        for ( PrivateExpirationData privateExpirationData : privateExpirationDataList ) {
            list.add( privateDataToDTO( privateExpirationData ) );
        }

        return list;
    }

    @Override
    public ExpirationDataVO DTOtoVO(ExpirationDataDTO expirationDataDTO) {
        if ( expirationDataDTO == null ) {
            return null;
        }

        IngredientDTO ingredientDTO = null;
        LocalDate expirationDate = null;
        boolean isNoExpiration = false;

        ingredientDTO = expirationDataDTO.getIngredientDTO();
        expirationDate = expirationDataDTO.getExpirationDate();
        isNoExpiration = expirationDataDTO.getIsNoExpiration();

        ExpirationDataVO expirationDataVO = new ExpirationDataVO( ingredientDTO, expirationDate, isNoExpiration );

        return expirationDataVO;
    }
}
