package zooyue.cn.service;


import zooyue.cn.dto.UserLoginDTO;
import zooyue.cn.entity.User;

public interface UserService {
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    User getUserByUser(User user);
}
