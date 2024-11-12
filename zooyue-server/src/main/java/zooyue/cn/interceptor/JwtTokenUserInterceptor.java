package zooyue.cn.interceptor;


import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import zooyue.cn.constant.JwtClaimsConstant;
import zooyue.cn.context.BaseContext;
import zooyue.cn.properties.JwtProperties;
import zooyue.cn.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源


        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());
        log.info(token);

        //2、校验令牌
        try {
            if(token != null) {

                log.info("jwt校验:{}", jwtProperties.getUserSecretKey());
                Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
                Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
                log.info("当前用户id：", userId);
                BaseContext.setCurrentId(userId);
            }

            //3、通过，放行
            return true;
        } catch (Exception ex) {
            System.out.println("错误信息");
            System.out.println(ex.getMessage());
//            验证失败无登录直接放行
            return true;
        }
    }
}
