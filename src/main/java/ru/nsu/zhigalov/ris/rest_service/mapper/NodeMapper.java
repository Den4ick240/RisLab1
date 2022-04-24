package ru.nsu.zhigalov.ris.rest_service.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.nsu.zhigalov.ris.rest_service.dto.NodeDTO;
import ru.nsu.zhigalov.ris.rest_service.dto.TagDTO;
import ru.nsu.zhigalov.ris.rest_service.entity.Node;
import ru.nsu.zhigalov.ris.rest_service.entity.Tag;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface NodeMapper {
    TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    @Mapping(target = "username", source = "usr")
    @Mapping(target = "longitude", source = "lon")
    @Mapping(target = "latitude", source = "lat")
    NodeDTO entityToDto(Node node);

    @InheritInverseConfiguration
//    @Mapping(target = "tags", source = ".", qualifiedByName = "tagsWithIds")
    Node dtoToEntity(NodeDTO nodeDTO);

    @AfterMapping
    default void mapTags(@MappingTarget Node node) {
        node.getTags().forEach(tag -> {
            System.out.println(tag);
            tag.setNodeId(node.getId());
            System.out.println(tag);
        });
    }

    @InheritConfiguration
    List<NodeDTO> entitiesToDtos(List<Node> nodes);

//    @Named("tagsWithIds")
//    default List<Tag> tagDTOlistToTagList(NodeDTO nodeDTO) {
//        List<TagDTO> tagDTOs = nodeDTO.getTags();
//        if (tagDTOs == null) {
//            return null;
//        }
//
//        List<Tag> list1 = new ArrayList<>(tagDTOs.size());
//        for (TagDTO tagDTO : tagDTOs) {
//            Tag tag = tagMapper.dtoToEntity(tagDTO);
//            tag.setNode(nodeDTO.getId());
//            list1.add(tag);
//        }
//
//        return list1;
//    }


}
