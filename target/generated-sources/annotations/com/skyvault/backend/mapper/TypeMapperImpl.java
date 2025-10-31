package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.TypeDto;
import com.skyvault.backend.model.Type;
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
public class TypeMapperImpl implements TypeMapper {

    @Override
    public TypeDto toDto(Type type) {
        if ( type == null ) {
            return null;
        }

        TypeDto typeDto = new TypeDto();

        typeDto.setAircraftCount( getAircraftCount( type ) );
        typeDto.setId( type.getId() );
        typeDto.setName( type.getName() );
        typeDto.setDescription( type.getDescription() );

        return typeDto;
    }

    @Override
    public List<TypeDto> toDtoList(List<Type> types) {
        if ( types == null ) {
            return null;
        }

        List<TypeDto> list = new ArrayList<TypeDto>( types.size() );
        for ( Type type : types ) {
            list.add( toDto( type ) );
        }

        return list;
    }
}
