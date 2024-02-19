package com.novel.zuul.config.jwt;

import com.novel.api.exception.BusinessException;
import com.novel.core.consts.SystemConsts;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.utils.JwtUtils;
import com.novel.zuul.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * @author 昴星
 * @date 2023-10-06 20:22
 * @explain
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRealm extends AuthorizingRealm {
    private final TokenService tokenService;

    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //认证
    //这个token就是从过滤器中传入的jwtToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

        String jwt = (String) token.getPrincipal();
        if (jwt == null) {
            throw new NullPointerException("jwtToken 不允许为空");
        }
        //获得token->id
        Long id = JwtUtils.parseToken(jwt, SystemConsts.JWT_TOKEN_KEY);
        Boolean matchToken = tokenService.isMatchToken(id, jwt);
        if(matchToken) return new SimpleAuthenticationInfo(jwt,jwt,"JwtRealm");
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名
        throw new BusinessException(ErrorStatusEnums.RES_TOKEN_ERR);
    }

}