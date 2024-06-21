package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.AccountRole;

@Mapper
public interface AccountRoleMapper {
    void save(AccountRole accountRole);
}
