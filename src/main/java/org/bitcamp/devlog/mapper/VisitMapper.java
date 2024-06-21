package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Visit;

import java.util.List;
import java.util.Map;

@Mapper
public interface VisitMapper {

    int findByAccountIdAndVisitDate(Map<String, Object> map);

    List<Visit> findAllByAccountId(String account_id);

    Long findVisitCountByAccountId(Long accountId);
}
