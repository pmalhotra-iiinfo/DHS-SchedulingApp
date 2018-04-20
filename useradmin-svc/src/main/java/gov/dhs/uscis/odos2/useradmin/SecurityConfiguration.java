package gov.dhs.uscis.odos2.useradmin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.extensions.saml2.config.SAMLConfigurer.saml;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Value("${security.saml2.metadata-url}")
    String metadataUrl;

    @Value("${server.ssl.key-alias}")
    String keyAlias;

    @Value("${server.ssl.key-store-password}")
    String password;

    @Value("${server.port}")
    String port;

    @Value("${server.ssl.key-store}")
    String keyStoreFilePath;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/saml*").permitAll()
//                .antMatchers("/auth*").authenticated()
//                .and()
//                .apply(saml())
//                .serviceProvider()
//                .keyStore()
//                .storeFilePath(this.keyStoreFilePath)
//                .password(this.password)
//                .keyname(this.keyAlias)
//                .keyPassword(this.password)
//                .and()
//                .protocol("https")
//                .hostname(String.format("%s:%s", "localhost", this.port))
//                .basePath("/")
//                .and()
//                .identityProvider()
//                .metadataFilePath(this.metadataUrl);
    }

}
