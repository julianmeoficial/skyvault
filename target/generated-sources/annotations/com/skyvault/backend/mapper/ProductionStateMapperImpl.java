package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.ProductionStateDto;
import com.skyvault.backend.model.ProductionState;
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
public class ProductionStateMapperImpl implements ProductionStateMapper {

    @Override
    public ProductionStateDto toDto(ProductionState productionState) {
        if ( productionState == null ) {
            return null;
        }

        ProductionStateDto productionStateDto = new ProductionStateDto();

        productionStateDto.setAircraftCount( getAircraftCount( productionState ) );
        productionStateDto.setId( productionState.getId() );
        productionStateDto.setName( productionState.getName() );
        productionStateDto.setDescription( productionState.getDescription() );
        productionStateDto.setIsActive( productionState.getIsActive() );

        return productionStateDto;
    }

    @Override
    public List<ProductionStateDto> toDtoList(List<ProductionState> productionStates) {
        if ( productionStates == null ) {
            return null;
        }

        List<ProductionStateDto> list = new ArrayList<ProductionStateDto>( productionStates.size() );
        for ( ProductionState productionState : productionStates ) {
            list.add( toDto( productionState ) );
        }

        return list;
    }
}
