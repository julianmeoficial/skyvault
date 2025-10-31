package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.SearchSuggestionDto;
import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.model.Family;
import com.skyvault.backend.model.Manufacturer;
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
public class SearchMapperImpl implements SearchMapper {

    @Override
    public SearchSuggestionDto aircraftToSuggestion(AircraftModel aircraft) {
        if ( aircraft == null ) {
            return null;
        }

        SearchSuggestionDto searchSuggestionDto = new SearchSuggestionDto();

        searchSuggestionDto.setText( aircraft.getDisplayName() );
        searchSuggestionDto.setSubtitle( getAircraftSubtitle( aircraft ) );
        searchSuggestionDto.setImageUrl( getAircraftThumbnail( aircraft ) );
        searchSuggestionDto.setId( aircraft.getId() );

        searchSuggestionDto.setType( "aircraft" );
        searchSuggestionDto.setMatchCount( 1 );

        return searchSuggestionDto;
    }

    @Override
    public SearchSuggestionDto manufacturerToSuggestion(Manufacturer manufacturer) {
        if ( manufacturer == null ) {
            return null;
        }

        SearchSuggestionDto searchSuggestionDto = new SearchSuggestionDto();

        searchSuggestionDto.setText( manufacturer.getName() );
        searchSuggestionDto.setSubtitle( manufacturer.getCountry() );
        searchSuggestionDto.setMatchCount( getManufacturerAircraftCount( manufacturer ) );
        searchSuggestionDto.setId( manufacturer.getId() );

        searchSuggestionDto.setType( "manufacturer" );

        return searchSuggestionDto;
    }

    @Override
    public SearchSuggestionDto familyToSuggestion(Family family) {
        if ( family == null ) {
            return null;
        }

        SearchSuggestionDto searchSuggestionDto = new SearchSuggestionDto();

        searchSuggestionDto.setText( family.getName() );
        searchSuggestionDto.setSubtitle( getFamilySubtitle( family ) );
        searchSuggestionDto.setMatchCount( getFamilyAircraftCount( family ) );
        searchSuggestionDto.setId( family.getId() );

        searchSuggestionDto.setType( "family" );

        return searchSuggestionDto;
    }

    @Override
    public List<SearchSuggestionDto> aircraftToSuggestionList(List<AircraftModel> aircraft) {
        if ( aircraft == null ) {
            return null;
        }

        List<SearchSuggestionDto> list = new ArrayList<SearchSuggestionDto>( aircraft.size() );
        for ( AircraftModel aircraftModel : aircraft ) {
            list.add( aircraftToSuggestion( aircraftModel ) );
        }

        return list;
    }

    @Override
    public List<SearchSuggestionDto> manufacturerToSuggestionList(List<Manufacturer> manufacturers) {
        if ( manufacturers == null ) {
            return null;
        }

        List<SearchSuggestionDto> list = new ArrayList<SearchSuggestionDto>( manufacturers.size() );
        for ( Manufacturer manufacturer : manufacturers ) {
            list.add( manufacturerToSuggestion( manufacturer ) );
        }

        return list;
    }

    @Override
    public List<SearchSuggestionDto> familyToSuggestionList(List<Family> families) {
        if ( families == null ) {
            return null;
        }

        List<SearchSuggestionDto> list = new ArrayList<SearchSuggestionDto>( families.size() );
        for ( Family family : families ) {
            list.add( familyToSuggestion( family ) );
        }

        return list;
    }
}
