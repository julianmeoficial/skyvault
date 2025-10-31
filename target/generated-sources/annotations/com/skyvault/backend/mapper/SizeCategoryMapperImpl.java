package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.SizeCategoryDto;
import com.skyvault.backend.model.SizeCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T14:48:59-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SizeCategoryMapperImpl implements SizeCategoryMapper {

    @Override
    public SizeCategoryDto toDto(SizeCategory sizeCategory) {
        if ( sizeCategory == null ) {
            return null;
        }

        SizeCategoryDto sizeCategoryDto = new SizeCategoryDto();

        sizeCategoryDto.setAircraftCount( getAircraftCount( sizeCategory ) );
        sizeCategoryDto.setPassengerRange( formatPassengerRange( sizeCategory ) );
        sizeCategoryDto.setId( sizeCategory.getId() );
        sizeCategoryDto.setName( sizeCategory.getName() );
        sizeCategoryDto.setDescription( sizeCategory.getDescription() );
        sizeCategoryDto.setMinPassengers( sizeCategory.getMinPassengers() );
        sizeCategoryDto.setMaxPassengers( sizeCategory.getMaxPassengers() );

        return sizeCategoryDto;
    }

    @Override
    public List<SizeCategoryDto> toDtoList(List<SizeCategory> sizeCategories) {
        if ( sizeCategories == null ) {
            return null;
        }

        List<SizeCategoryDto> list = new ArrayList<SizeCategoryDto>( sizeCategories.size() );
        for ( SizeCategory sizeCategory : sizeCategories ) {
            list.add( toDto( sizeCategory ) );
        }

        return list;
    }
}
