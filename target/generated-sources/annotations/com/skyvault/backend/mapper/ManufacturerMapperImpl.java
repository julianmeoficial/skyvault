package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.ManufacturerDto;
import com.skyvault.backend.dto.response.ManufacturerSummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import com.skyvault.backend.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T14:48:59-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ManufacturerMapperImpl implements ManufacturerMapper {

    @Override
    public ManufacturerDto toDto(Manufacturer manufacturer) {
        if ( manufacturer == null ) {
            return null;
        }

        ManufacturerDto manufacturerDto = new ManufacturerDto();

        manufacturerDto.setActiveAircraftCount( getActiveAircraftCount( manufacturer ) );
        manufacturerDto.setFamilyCount( getFamilyCount( manufacturer ) );
        manufacturerDto.setId( manufacturer.getId() );
        manufacturerDto.setName( manufacturer.getName() );
        manufacturerDto.setCountry( manufacturer.getCountry() );
        manufacturerDto.setFoundedDate( manufacturer.getFoundedDate() );
        manufacturerDto.setDescription( manufacturer.getDescription() );
        manufacturerDto.setWebsiteUrl( manufacturer.getWebsiteUrl() );
        manufacturerDto.setCreatedAt( manufacturer.getCreatedAt() );
        manufacturerDto.setUpdatedAt( manufacturer.getUpdatedAt() );

        return manufacturerDto;
    }

    @Override
    public ManufacturerSummaryDto toSummaryDto(Manufacturer manufacturer) {
        if ( manufacturer == null ) {
            return null;
        }

        ManufacturerSummaryDto manufacturerSummaryDto = new ManufacturerSummaryDto();

        manufacturerSummaryDto.setId( manufacturer.getId() );
        manufacturerSummaryDto.setName( manufacturer.getName() );
        manufacturerSummaryDto.setCountry( manufacturer.getCountry() );

        return manufacturerSummaryDto;
    }

    @Override
    public List<ManufacturerDto> toDtoList(List<Manufacturer> manufacturers) {
        if ( manufacturers == null ) {
            return null;
        }

        List<ManufacturerDto> list = new ArrayList<ManufacturerDto>( manufacturers.size() );
        for ( Manufacturer manufacturer : manufacturers ) {
            list.add( toDto( manufacturer ) );
        }

        return list;
    }

    @Override
    public List<ManufacturerSummaryDto> toSummaryDtoList(List<Manufacturer> manufacturers) {
        if ( manufacturers == null ) {
            return null;
        }

        List<ManufacturerSummaryDto> list = new ArrayList<ManufacturerSummaryDto>( manufacturers.size() );
        for ( Manufacturer manufacturer : manufacturers ) {
            list.add( toSummaryDto( manufacturer ) );
        }

        return list;
    }

    @Override
    public PagedResponseDto<ManufacturerDto> toPagedResponse(Page<Manufacturer> page) {
        if ( page == null ) {
            return null;
        }

        PagedResponseDto<ManufacturerDto> pagedResponseDto = new PagedResponseDto<ManufacturerDto>();

        if ( page.hasContent() ) {
            pagedResponseDto.setContent( toDtoList( page.getContent() ) );
        }
        pagedResponseDto.setPage( pageToMetadata( page ) );

        return pagedResponseDto;
    }
}
