package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.AircraftCardDto;
import com.skyvault.backend.dto.response.AircraftDetailDto;
import com.skyvault.backend.dto.response.AircraftSummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import com.skyvault.backend.dto.response.ProductionStateDto;
import com.skyvault.backend.dto.response.SizeCategoryDto;
import com.skyvault.backend.dto.response.TypeDto;
import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.model.Manufacturer;
import com.skyvault.backend.model.ProductionState;
import com.skyvault.backend.model.SizeCategory;
import com.skyvault.backend.model.Type;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-24T16:01:06-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class AircraftMapperImpl implements AircraftMapper {

    private final ManufacturerMapper manufacturerMapper;
    private final FamilyMapper familyMapper;
    private final ImageMapper imageMapper;
    private final SpecificationsMapper specificationsMapper;
    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_0159776256 = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );

    @Autowired
    public AircraftMapperImpl(ManufacturerMapper manufacturerMapper, FamilyMapper familyMapper, ImageMapper imageMapper, SpecificationsMapper specificationsMapper) {

        this.manufacturerMapper = manufacturerMapper;
        this.familyMapper = familyMapper;
        this.imageMapper = imageMapper;
        this.specificationsMapper = specificationsMapper;
    }

    @Override
    public AircraftDetailDto toDetailDto(AircraftModel aircraft) {
        if ( aircraft == null ) {
            return null;
        }

        AircraftDetailDto aircraftDetailDto = new AircraftDetailDto();

        aircraftDetailDto.setManufacturer( manufacturerMapper.toDto( aircraft.getManufacturer() ) );
        aircraftDetailDto.setFamily( familyMapper.toDto( aircraft.getFamily() ) );
        aircraftDetailDto.setType( typeToTypeDto( aircraft.getType() ) );
        aircraftDetailDto.setProductionState( productionStateToProductionStateDto( aircraft.getProductionState() ) );
        aircraftDetailDto.setSizeCategory( sizeCategoryToSizeCategoryDto( aircraft.getSizeCategory() ) );
        if ( aircraft.hasSpecifications() ) {
            aircraftDetailDto.setSpecifications( specificationsMapper.toDto( aircraft.getSpecifications() ) );
        }
        if ( aircraft.hasImages() ) {
            aircraftDetailDto.setImages( imageMapper.toDtoList( aircraft.getImages() ) );
        }
        if ( aircraft.getFirstFlightDate() != null ) {
            aircraftDetailDto.setFirstFlightDate( dateTimeFormatter_yyyy_MM_dd_0159776256.format( aircraft.getFirstFlightDate() ) );
        }
        aircraftDetailDto.setId( aircraft.getId() );
        aircraftDetailDto.setName( aircraft.getName() );
        aircraftDetailDto.setModel( aircraft.getModel() );
        aircraftDetailDto.setDisplayName( aircraft.getDisplayName() );
        aircraftDetailDto.setDescription( aircraft.getDescription() );
        aircraftDetailDto.setIntroductionYear( aircraft.getIntroductionYear() );
        aircraftDetailDto.setTypicalPassengers( aircraft.getTypicalPassengers() );
        aircraftDetailDto.setMaxPassengers( aircraft.getMaxPassengers() );
        aircraftDetailDto.setRangeKm( aircraft.getRangeKm() );
        aircraftDetailDto.setCruiseSpeedKnots( aircraft.getCruiseSpeedKnots() );
        aircraftDetailDto.setMinCrew( aircraft.getMinCrew() );
        aircraftDetailDto.setIsActive( aircraft.getIsActive() );
        aircraftDetailDto.setCreatedAt( aircraft.getCreatedAt() );
        aircraftDetailDto.setUpdatedAt( aircraft.getUpdatedAt() );

        afterMappingDetailDto( aircraftDetailDto, aircraft );

        return aircraftDetailDto;
    }

    @Override
    public AircraftSummaryDto toSummaryDto(AircraftModel aircraft) {
        if ( aircraft == null ) {
            return null;
        }

        AircraftSummaryDto aircraftSummaryDto = new AircraftSummaryDto();

        aircraftSummaryDto.setManufacturerName( aircraftManufacturerName( aircraft ) );
        aircraftSummaryDto.setId( aircraft.getId() );
        aircraftSummaryDto.setName( aircraft.getName() );

        return aircraftSummaryDto;
    }

    @Override
    public AircraftCardDto toCardDto(AircraftModel aircraft) {
        if ( aircraft == null ) {
            return null;
        }

        AircraftCardDto aircraftCardDto = new AircraftCardDto();

        aircraftCardDto.setManufacturer( manufacturerMapper.toSummaryDto( aircraft.getManufacturer() ) );
        aircraftCardDto.setFamily( familyMapper.toSummaryDto( aircraft.getFamily() ) );
        aircraftCardDto.setThumbnailUrl( getThumbnailUrl( aircraft ) );
        aircraftCardDto.setId( aircraft.getId() );
        aircraftCardDto.setName( aircraft.getName() );
        aircraftCardDto.setModel( aircraft.getModel() );
        aircraftCardDto.setDisplayName( aircraft.getDisplayName() );
        aircraftCardDto.setIntroductionYear( aircraft.getIntroductionYear() );
        aircraftCardDto.setTypicalPassengers( aircraft.getTypicalPassengers() );
        aircraftCardDto.setMaxPassengers( aircraft.getMaxPassengers() );
        aircraftCardDto.setRangeKm( aircraft.getRangeKm() );
        aircraftCardDto.setCruiseSpeedKnots( aircraft.getCruiseSpeedKnots() );
        aircraftCardDto.setIsActive( aircraft.getIsActive() );

        afterMappingCardDto( aircraftCardDto, aircraft );

        return aircraftCardDto;
    }

    @Override
    public List<AircraftCardDto> toCardDtoList(List<AircraftModel> aircraft) {
        if ( aircraft == null ) {
            return null;
        }

        List<AircraftCardDto> list = new ArrayList<AircraftCardDto>( aircraft.size() );
        for ( AircraftModel aircraftModel : aircraft ) {
            list.add( toCardDto( aircraftModel ) );
        }

        return list;
    }

    @Override
    public List<AircraftDetailDto> toDetailDtoList(List<AircraftModel> aircraft) {
        if ( aircraft == null ) {
            return null;
        }

        List<AircraftDetailDto> list = new ArrayList<AircraftDetailDto>( aircraft.size() );
        for ( AircraftModel aircraftModel : aircraft ) {
            list.add( toDetailDto( aircraftModel ) );
        }

        return list;
    }

    @Override
    public List<AircraftSummaryDto> toSummaryDtoList(List<AircraftModel> aircraft) {
        if ( aircraft == null ) {
            return null;
        }

        List<AircraftSummaryDto> list = new ArrayList<AircraftSummaryDto>( aircraft.size() );
        for ( AircraftModel aircraftModel : aircraft ) {
            list.add( toSummaryDto( aircraftModel ) );
        }

        return list;
    }

    @Override
    public PagedResponseDto<AircraftCardDto> toPagedCardResponse(Page<AircraftModel> page) {
        if ( page == null ) {
            return null;
        }

        PagedResponseDto<AircraftCardDto> pagedResponseDto = new PagedResponseDto<AircraftCardDto>();

        if ( page.hasContent() ) {
            pagedResponseDto.setContent( toCardDtoList( page.getContent() ) );
        }
        pagedResponseDto.setPage( pageToMetadata( page ) );

        return pagedResponseDto;
    }

    protected TypeDto typeToTypeDto(Type type) {
        if ( type == null ) {
            return null;
        }

        TypeDto typeDto = new TypeDto();

        typeDto.setId( type.getId() );
        typeDto.setName( type.getName() );
        typeDto.setDescription( type.getDescription() );

        return typeDto;
    }

    protected ProductionStateDto productionStateToProductionStateDto(ProductionState productionState) {
        if ( productionState == null ) {
            return null;
        }

        ProductionStateDto productionStateDto = new ProductionStateDto();

        productionStateDto.setId( productionState.getId() );
        productionStateDto.setName( productionState.getName() );
        productionStateDto.setDescription( productionState.getDescription() );
        productionStateDto.setIsActive( productionState.getIsActive() );

        return productionStateDto;
    }

    protected SizeCategoryDto sizeCategoryToSizeCategoryDto(SizeCategory sizeCategory) {
        if ( sizeCategory == null ) {
            return null;
        }

        SizeCategoryDto sizeCategoryDto = new SizeCategoryDto();

        sizeCategoryDto.setId( sizeCategory.getId() );
        sizeCategoryDto.setName( sizeCategory.getName() );
        sizeCategoryDto.setDescription( sizeCategory.getDescription() );
        sizeCategoryDto.setMinPassengers( sizeCategory.getMinPassengers() );
        sizeCategoryDto.setMaxPassengers( sizeCategory.getMaxPassengers() );
        sizeCategoryDto.setPassengerRange( sizeCategory.getPassengerRange() );

        return sizeCategoryDto;
    }

    private String aircraftManufacturerName(AircraftModel aircraftModel) {
        if ( aircraftModel == null ) {
            return null;
        }
        Manufacturer manufacturer = aircraftModel.getManufacturer();
        if ( manufacturer == null ) {
            return null;
        }
        String name = manufacturer.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
