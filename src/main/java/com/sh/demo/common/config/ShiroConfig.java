package com.sh.demo.common.config;

import com.sh.demo.common.shiro.ShiroRealm;
import com.sh.demo.common.shiro.ShiroSessionIdGenerator;
import com.sh.demo.common.shiro.ShiroSessionManager;
import com.sh.demo.common.util.SHA256Util;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description Shiro配置类
 */
@Configuration
public class ShiroConfig {

    private final String CACHE_KEY = "shiro:cache:";
    private final String SESSION_KEY = "shiro:session:";

//    //Redis配置
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private int port;
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//    @Value("${spring.redis.password}")
//    private String password;

    /**
     * 开启Shiro-aop注解支持
     * @Attention 使用代理方式所以需要开启代码支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    /**
//         * Shiro内置过滤器，可以实现权限相关的拦截器
//         *    常用的过滤器：
//         *       anon: 无需认证（登录）可以访问
//         *       authc: 必须认证才可以访问
//         *       user: 如果使用rememberMe的功能可以直接访问
//         *       perms： 该资源必须得到资源权限才可以访问
//         *       role: 该资源必须得到角色权限才可以访问
//         */

    /**
     * Shiro基础配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 注意过滤器配置顺序不能颠倒
        // 配置过滤:不会被拦截的链接
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/userLogin/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        // 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
//        shiroFilterFactoryBean.setLoginUrl("/userLogin/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义Ssession管理
//        securityManager.setSessionManager(sessionManager());
        // 自定义Cache实现
//        securityManager.setCacheManager(cacheManager());
        // 自定义Realm验证
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    /**
     * 身份验证器
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 凭证匹配器
     * 将密码校验交给Shiro的SimpleAuthenticationInfo进行处理,在这里做匹配配置
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用SHA256算法;
        shaCredentialsMatcher.setHashAlgorithmName(SHA256Util.HASH_ALGORITHM_NAME);
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        shaCredentialsMatcher.setHashIterations(SHA256Util.HASH_ITERATIONS);
        return shaCredentialsMatcher;
    }
//
//    /**
//     * 配置Redis管理器
//     * @Attention 使用的是shiro-redis开源插件
//     */
//    @Bean
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        redisManager.setTimeout(timeout);
//        redisManager.setPassword(password);
//        return redisManager;
//    }

    /**
     * 配置Cache管理器
     * 用于往Redis存储权限和角色标识
     * @Attention 使用的是shiro-redis开源插件
     */
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        redisCacheManager.setKeyPrefix(CACHE_KEY);
//        // 配置缓存的话要求放在session里面的实体类必须有个id标识
//        redisCacheManager.setPrincipalIdFieldName("userId");
//        return redisCacheManager;
//    }

    /**
     * SessionID生成器
     */
    @Bean
    public ShiroSessionIdGenerator sessionIdGenerator(){
        return new ShiroSessionIdGenerator();
    }

//    /**
//     * 配置RedisSessionDAO
//     * @Attention 使用的是shiro-redis开源插件
//     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
//        redisSessionDAO.setKeyPrefix(SESSION_KEY);
//        redisSessionDAO.setExpire(timeout);
//        return redisSessionDAO;
//    }

//    /**
//     * 配置Session管理器
//     */
//    @Bean
//    public SessionManager sessionManager() {
//        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
//        shiroSessionManager.setSessionDAO(redisSessionDAO());
//        return shiroSessionManager;
//    }
}