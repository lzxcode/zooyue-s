package zooyue.cn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import zooyue.cn.constant.MessageConstant;
import zooyue.cn.constant.StatusConstant;
import zooyue.cn.dto.UserLoginDTO;
import zooyue.cn.entity.User;
import zooyue.cn.exception.AccountLockedException;
import zooyue.cn.exception.AccountNotFoundException;
import zooyue.cn.exception.PasswordErrorException;
import zooyue.cn.mapper.UserMapper;
import zooyue.cn.service.UserService;
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login( UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUserName();
        String password = userLoginDTO.getPassword();
    log.info(username);
        //1、根据用户名查询数据库中的数据
        User user = userMapper.getByUsername(username);
        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        return user;

    }

    @Override
    public User getUserByUser(User user) {
        return  userMapper.getUserByUser(user);
    }
}
