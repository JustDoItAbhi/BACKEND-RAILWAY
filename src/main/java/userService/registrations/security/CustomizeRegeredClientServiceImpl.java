package userService.registrations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import userService.registrations.exceptions.UserExceptions;
import userService.registrations.security.authEntity.Client;
import userService.registrations.security.authRepo.ClientRepository;
import userService.registrations.security.dtos.ClientRequestDto;
import userService.registrations.security.authService.JpaRegisteredClientRepository;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Transactional
@Service
public class CustomizeRegeredClientServiceImpl implements CustomizeRegeredClientService{// oicd registered client implementations
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;// password encoder
    @Autowired
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;// jpa client repository
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public RegisteredClient createRegeretedClient(ClientRequestDto dto) {// method to create client
        Set<AuthorizationGrantType> authorizationGrantTypes=new HashSet<>();// hashset to store grant type
        authorizationGrantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
        authorizationGrantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
        authorizationGrantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
        authorizationGrantTypes.add(AuthorizationGrantType.DEVICE_CODE);
        authorizationGrantTypes.add(AuthorizationGrantType.JWT_BEARER);
        authorizationGrantTypes.add(AuthorizationGrantType.TOKEN_EXCHANGE);
//            .authorizationGrantType(AuthorizationGrantType.PASSWORD)

        Set<String> scopes=new HashSet<>();// set scopes for client to permit users
        scopes.add(OidcScopes.PROFILE);
        scopes.add(OidcScopes.OPENID);
        scopes.add(OidcScopes.ADDRESS);
        scopes.add(OidcScopes.EMAIL);
        scopes.add(OidcScopes.PHONE);

        RegisteredClient.Builder oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(dto.getClientId())// set client id
                .clientSecret(passwordEncoder.encode(dto.getClientSecret()))// sent password encoded
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .redirectUri(dto.getRedirectUris())
                .clientName(dto.getClientName())
                .clientIdIssuedAt(Instant.now())
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .postLogoutRedirectUri(dto.getPostLogoutRedirectUris())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build());
        for (AuthorizationGrantType grandType:authorizationGrantTypes){
            oidcClient.authorizationGrantType(grandType);
        }

        RegisteredClient client=oidcClient.build();
        jpaRegisteredClientRepository.save(client);// save to database
        return client;
    }

    @Override
    public boolean deleteClient(String clientId) {
       clientRepository.deleteById(clientId);
        return false;
    }

    @Override
    public RegisteredClient updateClient(String id,UUID uid, ClientRequestDto dto) {
        Set<AuthorizationGrantType> authorizationGrantTypes=new HashSet<>();// hashset to store grant type
        authorizationGrantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
        authorizationGrantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
        authorizationGrantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
        authorizationGrantTypes.add(AuthorizationGrantType.DEVICE_CODE);
        authorizationGrantTypes.add(AuthorizationGrantType.JWT_BEARER);
        authorizationGrantTypes.add(AuthorizationGrantType.TOKEN_EXCHANGE);
//            .authorizationGrantType(AuthorizationGrantType.PASSWORD)

        Set<String> scopes=new HashSet<>();// set scopes for client to permit users
        scopes.add(OidcScopes.PROFILE);
        scopes.add(OidcScopes.OPENID);
        scopes.add(OidcScopes.ADDRESS);
        scopes.add(OidcScopes.EMAIL);
        scopes.add(OidcScopes.PHONE);

        Optional<Client>client=clientRepository.findByClientName(dto.getClientName());
        if(client.isEmpty()){
            throw new UserExceptions("CLIENT NOT FOUND "+id);
        }
        RegisteredClient rClient=jpaRegisteredClientRepository.findByClientId(dto.getClientId());
        if(rClient==null){
            throw new UserExceptions("REGISTER CLEINT NOT FOUND BY ID "+id);
        }
        RegisteredClient.Builder oidcClient =RegisteredClient.withId(id)
                .clientId(dto.getClientId())
                 .clientSecret(passwordEncoder.encode(dto.getClientSecret()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .redirectUri(dto.getRedirectUris())
                .clientName(dto.getClientName())
                .clientIdIssuedAt(Instant.now())
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .postLogoutRedirectUri(dto.getPostLogoutRedirectUris())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build());
        for (AuthorizationGrantType grandType:authorizationGrantTypes){
            oidcClient.authorizationGrantType(grandType);
        }
        RegisteredClient saveclient=oidcClient.build();
        jpaRegisteredClientRepository.save(saveclient);// save to database

        return saveclient;
    }
}