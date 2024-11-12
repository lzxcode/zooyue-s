package zooyue.cn.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zooyue.cn.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from zooyue.user where username = #{username}")
    User getByUsername(String username);

    User getUserByUser(User user);
}
