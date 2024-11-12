package zooyue.cn.controller.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zooyue.cn.constant.JwtClaimsConstant;
import zooyue.cn.context.BaseContext;
import zooyue.cn.dto.UserLoginDTO;
import zooyue.cn.entity.User;
import zooyue.cn.properties.JwtProperties;
import zooyue.cn.result.Result;
import zooyue.cn.service.UserService;
import zooyue.cn.utils.JwtUtil;
import zooyue.cn.vo.UserLoginVO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "用户端相关接口")
public class UserController {
 
     @Autowired
     private UserService userService;
     @Autowired
     private JwtProperties JwtProperties;
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO UserLoginDTO) {
        log.info("员工登录：{}", UserLoginDTO);

        User user = userService.login(UserLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        System.out.println("秘钥");
        System.out.println(JwtProperties.getUserSecretKey());
        String token = JwtUtil.createJWT(
                JwtProperties.getUserSecretKey(),
                JwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .name(user.getName())
                .token(token)
                .build();

        return Result.success(userLoginVO,"登录成功");
    }


    @GetMapping("/getUserInfo")
    public  Result<User> getUserInfo() {
        log.info("获取员工信息");
        System.out.println(BaseContext.getCurrentId());
        if(BaseContext.getCurrentId() == null)  {
             return  Result.error("无令牌信息");
        }

        User user = userService.getUserByUser(User.builder().id(BaseContext.getCurrentId()).build());
        return Result.success(user);
    }
}
