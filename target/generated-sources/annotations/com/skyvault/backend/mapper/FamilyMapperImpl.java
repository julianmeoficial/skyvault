package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.FamilyDto;
import com.skyvault.backend.dto.response.FamilySummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import com.skyvault.backend.model.Family;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T14:48:59-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class FamilyMapperImpl implements FamilyMapper {

    private final ManufacturerMapper manufacturerMapper;

    @Autowired
    public FamilyMapperImpl(ManufacturerMapper manufacturerMapper) {

        this.manufacturerMapper = manufacturerMapper;
    }

    @Override
    public FamilyDto toDto(Family family) {
        if ( family == null ) {
            return null;
        }

        FamilyDto familyDto = new FamilyDto();

        familyDto.setManufacturer( manufacturerMapper.toSummaryDto( family.getManufacturer() ) );
        familyDto.setActiveModelCount( getActiveModelCount( family ) );
        familyDto.setId( family.getId() );
        familyDto.setName( family.getName() );
        familyDto.setDescription( family.getDescription() );
        familyDto.setLaunchDate( family.getLaunchDate() );
        familyDto.setCategory( family.getCategory() );
        familyDto.setCreatedAt( family.getCreatedAt() );
        familyDto.setUpdatedAt( family.getUpdatedAt() );

        return familyDto;
    }

    @Override
    public FamilySummaryDto toSummaryDto(Family family) {
        if ( family == null ) {
            return null;
        }

        FamilySummaryDto familySummaryDto = new FamilySummaryDto();

        familySummaryDto.setId( family.getId() );
        familySummaryDto.setName( family.getName() );
        familySummaryDto.setCategory( family.getCategory() );

        return familySummaryDto;
    }

    @Override
    public List<FamilyDto> toDtoList(List<Family> families) {
        if ( families == null ) {
            return null;
        }

        List<FamilyDto> list = new ArrayList<FamilyDto>( families.size() );
        for ( Family family : families ) {
            list.add( toDto( family ) );
        }

        return list;
    }

    @Override
    public List<FamilySummaryDto> toSummaryDtoList(List<Family> families) {
        if ( families == null ) {
            return null;
        }

        List<FamilySummaryDto> list = new ArrayList<FamilySummaryDto>( families.size() );
        for ( Family family : families ) {
            list.add( toSummaryDto( family ) );
        }

        return list;
    }

    @Override
    public PagedResponseDto<FamilyDto> toPagedResponse(Page<Family> page) {
        if ( page == null ) {
            return null;
        }

        PagedResponseDto<FamilyDto> pagedResponseDto = new PagedResponseDto<FamilyDto>();

        if ( page.hasContent() ) {
            pagedResponseDto.setContent( toDtoList( page.getContent() ) );
        }
        pagedResponseDto.setPage( pageToMetadata( page ) );

        return pagedResponseDto;
    }
}
