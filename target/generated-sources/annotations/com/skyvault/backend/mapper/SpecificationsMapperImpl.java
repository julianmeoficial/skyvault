package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.SpecificationsDto;
import com.skyvault.backend.model.Specifications;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T14:48:59-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SpecificationsMapperImpl implements SpecificationsMapper {

    @Override
    public SpecificationsDto toDto(Specifications specifications) {
        if ( specifications == null ) {
            return null;
        }

        SpecificationsDto specificationsDto = new SpecificationsDto();

        specificationsDto.setLengthM( formatLength( specifications ) );
        specificationsDto.setWingspanM( formatWingspan( specifications ) );
        specificationsDto.setHeightM( formatHeight( specifications ) );
        specificationsDto.setMaxSpeedKmh( formatMaxSpeed( specifications ) );
        specificationsDto.setEmptyWeightKg( formatEmptyWeight( specifications ) );
        specificationsDto.setMaxTakeoffWeightKg( formatMaxTakeoffWeight( specifications ) );
        specificationsDto.setEngineCount( formatEngineCount( specifications ) );
        specificationsDto.setFuelCapacityLiters( formatFuelCapacity( specifications ) );
        specificationsDto.setEngineThrustN( formatEngineThrust( specifications ) );
        specificationsDto.setTotalThrustN( formatTotalThrust( specifications ) );
        if ( specifications.getWingAreaM2() != null ) {
            specificationsDto.setWingAreaM2( specifications.getWingAreaM2().toString() );
        }
        if ( specifications.getMaxLandingWeightKg() != null ) {
            specificationsDto.setMaxLandingWeightKg( specifications.getMaxLandingWeightKg().toString() );
        }
        if ( specifications.getMaxPayloadKg() != null ) {
            specificationsDto.setMaxPayloadKg( specifications.getMaxPayloadKg().toString() );
        }
        if ( specifications.getServiceCeilingM() != null ) {
            specificationsDto.setServiceCeilingM( String.valueOf( specifications.getServiceCeilingM() ) );
        }
        if ( specifications.getTakeoffDistanceM() != null ) {
            specificationsDto.setTakeoffDistanceM( String.valueOf( specifications.getTakeoffDistanceM() ) );
        }
        if ( specifications.getLandingDistanceM() != null ) {
            specificationsDto.setLandingDistanceM( String.valueOf( specifications.getLandingDistanceM() ) );
        }
        if ( specifications.getFuelConsumptionLph() != null ) {
            specificationsDto.setFuelConsumptionLph( String.valueOf( specifications.getFuelConsumptionLph() ) );
        }
        if ( specifications.getCabinLengthM() != null ) {
            specificationsDto.setCabinLengthM( specifications.getCabinLengthM().toString() );
        }
        if ( specifications.getCabinWidthM() != null ) {
            specificationsDto.setCabinWidthM( specifications.getCabinWidthM().toString() );
        }
        if ( specifications.getCabinHeightM() != null ) {
            specificationsDto.setCabinHeightM( specifications.getCabinHeightM().toString() );
        }
        if ( specifications.getCargoVolumeM3() != null ) {
            specificationsDto.setCargoVolumeM3( specifications.getCargoVolumeM3().toString() );
        }
        specificationsDto.setEngineManufacturer( specifications.getEngineManufacturer() );
        specificationsDto.setEngineModel( specifications.getEngineModel() );
        if ( specifications.getFirstClassSeats() != null ) {
            specificationsDto.setFirstClassSeats( String.valueOf( specifications.getFirstClassSeats() ) );
        }
        if ( specifications.getBusinessClassSeats() != null ) {
            specificationsDto.setBusinessClassSeats( String.valueOf( specifications.getBusinessClassSeats() ) );
        }
        if ( specifications.getEconomyClassSeats() != null ) {
            specificationsDto.setEconomyClassSeats( String.valueOf( specifications.getEconomyClassSeats() ) );
        }
        if ( specifications.getSeatPitchEconomyCm() != null ) {
            specificationsDto.setSeatPitchEconomyCm( String.valueOf( specifications.getSeatPitchEconomyCm() ) );
        }
        if ( specifications.getSeatWidthEconomyCm() != null ) {
            specificationsDto.setSeatWidthEconomyCm( String.valueOf( specifications.getSeatWidthEconomyCm() ) );
        }
        if ( specifications.getRangeWithMaxPaxKm() != null ) {
            specificationsDto.setRangeWithMaxPaxKm( String.valueOf( specifications.getRangeWithMaxPaxKm() ) );
        }
        if ( specifications.getRangeWithMaxPayloadKm() != null ) {
            specificationsDto.setRangeWithMaxPayloadKm( String.valueOf( specifications.getRangeWithMaxPayloadKm() ) );
        }
        specificationsDto.setCertificationAuthorities( specifications.getCertificationAuthorities() );
        if ( specifications.getNoiseLevelDb() != null ) {
            specificationsDto.setNoiseLevelDb( specifications.getNoiseLevelDb().toString() );
        }

        afterMapping( specificationsDto, specifications );

        return specificationsDto;
    }
}
