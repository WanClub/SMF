package insight.portal.dao;

import java.util.List;
import java.util.Map;

import insight.portal.model.UUserRole;


public interface UUserRoleMapper {
    int insert(UUserRole record);

    int insertSelective(UUserRole record);

	int deleteByUserId(Long id);

	int deleteRoleByUserIds(Map<String, Object> resultMap);

	List<Long> findUserIdByRoleId(Long id);
}