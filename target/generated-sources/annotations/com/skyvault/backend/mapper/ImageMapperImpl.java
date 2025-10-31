package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.ImageDto;
import com.skyvault.backend.model.Image;
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
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageDto toDto(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageDto imageDto = new ImageDto();

        imageDto.setId( image.getId() );
        imageDto.setUrl( image.getUrl() );
        imageDto.setAltText( image.getAltText() );
        imageDto.setImageType( image.getImageType() );
        imageDto.setFileFormat( image.getFileFormat() );
        imageDto.setFileSizeBytes( image.getFileSizeBytes() );
        imageDto.setIsPrimary( image.getIsPrimary() );
        imageDto.setIsComparison( image.getIsComparison() );
        imageDto.setDisplayOrder( image.getDisplayOrder() );
        imageDto.setCreatedAt( image.getCreatedAt() );

        afterMapping( imageDto, image );

        return imageDto;
    }

    @Override
    public List<ImageDto> toDtoList(List<Image> images) {
        if ( images == null ) {
            return null;
        }

        List<ImageDto> list = new ArrayList<ImageDto>( images.size() );
        for ( Image image : images ) {
            list.add( toDto( image ) );
        }

        return list;
    }
}
