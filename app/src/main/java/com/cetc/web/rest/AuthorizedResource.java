package com.cetc.web.rest;

import com.cetc.domain.Role;
import com.cetc.domain.User;
import com.cetc.repository.RoleRepository;
import com.cetc.repository.UserRepository;
import com.cetc.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authorize")
public class AuthorizedResource {

    @Autowired
    private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/login")
    public void login(String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!StringUtils.isEmpty(code)) {
            LinkedMultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add("client_id", authorizationCodeResourceDetails.getClientId());
            valueMap.add("client_secret", authorizationCodeResourceDetails.getClientSecret());
            valueMap.add("grant_type", authorizationCodeResourceDetails.getGrantType());
            valueMap.add("redirect_uri", authorizationCodeResourceDetails.getPreEstablishedRedirectUri());
            valueMap.add("code", code);
            Map<String, String> map = HttpUtils.doFrom(authorizationCodeResourceDetails.getAccessTokenUri(), valueMap, Map.class);
            System.out.println(map);

            //获取用户信息，说明这里主要目的就是通过资源服务器去获取用户信息
            Map principal = HttpUtils.doGet(resourceServerProperties.getUserInfoUri() + "?access_token=" + map.get("access_token"), Map.class);

            //这里通过本地登录单点登录
            String username = principal.get("name").toString();
            //如果用户存在则不添加，这里如果生产应用中，可以更具规则修改
            if (userRepository.findByUsername(username) == null) {
                Role role = roleRepository.findByRoleType(Role.RoleType.USER);
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(passwordEncoder.encode(username));
                newUser.getRoles().add(role);
                userRepository.save(newUser);
            }

            //这里通过本地登录的方式来获取会话
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("username", username);
            params.add("password", username);
            HttpEntity<LinkedMultiValueMap<String, ? extends Object>> httpEntity = new HttpEntity(params, httpHeaders);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/login";
            ResponseEntity<Object> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
            //将登录后的header原本的给浏览器，这就是当前浏览器的会话
            HttpHeaders headers = exchange.getHeaders();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                entry.getValue().stream().forEach(value -> response.addHeader(entry.getKey(), value));
            }
            //这个状态是根据security的返回数据设定的
            response.setStatus(exchange.getStatusCode().value());
        }
    }
}
