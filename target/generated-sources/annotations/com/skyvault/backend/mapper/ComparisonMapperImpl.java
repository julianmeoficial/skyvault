package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.dto.response.CompareResultDto;
import com.skyvault.backend.dto.response.ComparisonConfigDto;
import com.skyvault.backend.dto.response.ComparisonValueDto;
import com.skyvault.backend.model.AircraftModel;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T14:48:59-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ComparisonMapperImpl implements ComparisonMapper {

    private final AircraftMapper aircraftMapper;

    @Autowired
    public ComparisonMapperImpl(AircraftMapper aircraftMapper) {

        this.aircraftMapper = aircraftMapper;
    }

    @Override
    public CompareResultDto createCompareResult(List<AircraftModel> aircraftList, CompareRequestDto request) {
        if ( aircraftList == null && request == null ) {
            return null;
        }

        CompareResultDto compareResultDto = new CompareResultDto();

        if ( aircraftList != null ) {
            compareResultDto.setAircraft( aircraftMapper.toDetailDtoList( aircraftList ) );
            compareResultDto.setTotalAircraft( getListSize( aircraftList ) );
        }
        compareResultDto.setConfig( toConfigDto( request ) );
        compareResultDto.setComparedAt( getCurrentTimestamp() );

        return compareResultDto;
    }

    @Override
    public ComparisonConfigDto toConfigDto(CompareRequestDto request) {
        if ( request == null ) {
            return null;
        }

        ComparisonConfigDto comparisonConfigDto = new ComparisonConfigDto();

        comparisonConfigDto.setIncludeSpecifications( request.getIncludeSpecifications() );
        comparisonConfigDto.setIncludeImages( request.getIncludeImages() );
        comparisonConfigDto.setNormalizeUnits( request.getNormalizeUnits() );
        comparisonConfigDto.setUnitFormat( request.getUnitFormat() );

        afterMappingConfig( comparisonConfigDto, request );

        return comparisonConfigDto;
    }

    @Override
    public ComparisonValueDto createComparisonValue(Long aircraftId, String originalValue, String displayValue) {
        if ( aircraftId == null && originalValue == null && displayValue == null ) {
            return null;
        }

        ComparisonValueDto comparisonValueDto = new ComparisonValueDto();

        comparisonValueDto.setAircraftId( aircraftId );
        comparisonValueDto.setOriginalValue( originalValue );
        comparisonValueDto.setDisplayValue( displayValue );

        return comparisonValueDto;
    }
}
