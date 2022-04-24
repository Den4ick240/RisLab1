package ru.nsu.zhigalov.ris.rest_service.mapper;

import org.mapstruct.*;
import ru.nsu.zhigalov.ris.rest_service.dto.TagDTO;
import ru.nsu.zhigalov.ris.rest_service.entity.Tag;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mapping(target = "key", source = "k")
    @Mapping(target = "value", source = "v")
    TagDTO entityToDto(Tag tag);

    @InheritInverseConfiguration
    @Mapping(target = "nodeId", ignore = true)
    Tag dtoToEntity(TagDTO tagDTO);
//
//    @Named("tagDtoToEntityChild")
//    @InheritInverseConfiguration
//    @Mapping(target = "nodeId", source = "nodeId")
//    @Mapping(target = "k", source = "tagDTO.key")
//    @Mapping(target = "v", source = "tagDTO.value")
//    Tag dtoToEntityChild(TagDTO tagDTO, Long nodeId);

//    @InheritConfiguration
    List<TagDTO> entitiesToDtos(List<Tag> tags);
}
