package br.com.elo7.sonda.candidato.api.config.security;

import br.com.elo7.sonda.candidato.api.constants.IConstants;
import br.com.elo7.sonda.candidato.api.service.ITokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final SecurityService securityService;

    private final ITokenService tokenService;

    public SecurityConfigurations(SecurityService securityService, ITokenService tokenService) {
        this.securityService = securityService;
        this.tokenService = tokenService;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs/*").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                .antMatchers(HttpMethod.POST, IConstants.Controller.Security.PATH).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }

    //(js, css, imagens, etc.)
    @Override
    public void configure(WebSecurity web) throws Exception {
    }

//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}

}
