package ru.nsu.zhigalov.ris.rest_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.zhigalov.ris.rest_service.entity.Tag;
import ru.nsu.zhigalov.ris.rest_service.entity.TagId;

import java.util.Collection;

@Repository
public interface TagRepository extends JpaRepository<Tag, TagId> {
    void deleteByNodeIdAndKNotIn(Long nodeId, Collection<String> ks);
}
