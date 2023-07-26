package br.com.drop.demo.config;


import br.com.drop.demo.jwt.JwtAuthFilter;
import br.com.drop.demo.jwt.JwtService;
import br.com.drop.demo.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailServiceImpl userDetailServiceService;


    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter( jwtService,userDetailServiceService);
    }






    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/product/**").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/api/product/show/all").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/product/save").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/usuario/insert").permitAll()
                .antMatchers(HttpMethod.POST,"/api/usuario/auth").permitAll()
                /*.antMatchers(HttpMethod.GET, "/api/usuario/all").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/usuario/{user_id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/filtered/search").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/{user_id}/update").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/{user_id}/deactivate/account").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/usuario/insert").permitAll()
                .antMatchers(HttpMethod.POST, "/api/usuario/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/api/product/show/all").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/product/save").hasRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
