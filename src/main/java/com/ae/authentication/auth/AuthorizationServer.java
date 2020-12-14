package com.ae.authentication.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private final String APP_NAME = "androidapp";
    private final String SECRET_TEST = "123456pppaaa";
    private final String SCOPE_READ = "read";
    private final String SCOPE_WRITE = "write";
    private final String TYPE_AUTH = "password";
    private final String REFRESH_TOKEN = "refresh_token";
    private final Integer TOKEN_TIME = 3600;


    final private BCryptPasswordEncoder passwordEncoder;
    final private AuthenticationManager authenticationManager;
    final private JwtAccessTokenConverter jwtAccessTokenConverter;
    final private JwtTokenStore jwtTokenStore;

    @Autowired
    public AuthorizationServer(BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                               JwtAccessTokenConverter jwtAccessTokenConverter, JwtTokenStore jwtTokenStore) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.jwtTokenStore = jwtTokenStore;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(jwtTokenStore);
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(APP_NAME)
                .secret(passwordEncoder.encode(SECRET_TEST))
                .scopes(SCOPE_READ, SCOPE_WRITE)
                .authorizedGrantTypes(TYPE_AUTH, REFRESH_TOKEN)
                .accessTokenValiditySeconds(TOKEN_TIME)
                .refreshTokenValiditySeconds(TOKEN_TIME);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");
    }
}
