package br.com.guilhermefausto.teavisei.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

import br.com.guilhermefausto.teavisei.model.PerfilEnum;
import br.com.guilhermefausto.teavisei.repository.UsuarioRepository;


@Configuration
@EnableWebSecurity
@Profile(value = {"test","prod","default"})
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//configurações de autenticação(login, etc)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/estabelecimentos/moderacao/**").hasAnyRole(PerfilEnum.MODERADOR.toString(),
																								PerfilEnum.ADMINISTRADOR.toString())
					
					.antMatchers(HttpMethod.GET, "/estabelecimentos/**").permitAll()
					
					.antMatchers(HttpMethod.DELETE, "/estabelecimentos/*").hasRole(PerfilEnum.ADMINISTRADOR.toString())
					.antMatchers(HttpMethod.PUT, "/estabelecimentos/*").hasRole(PerfilEnum.ADMINISTRADOR.toString())
					
					.antMatchers(HttpMethod.POST, "/auth").permitAll()
					.antMatchers(HttpMethod.POST, "/signup").permitAll()
					//.antMatchers("/**").permitAll()
					.anyRequest().authenticated()
			.and()
					//configurando projeto para não criar sessão
					.csrf().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
					.addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
			
	}
	
	//configurações de recursos estáticos(js, css, imagens, etc)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html","/v3/api-docs","/v2/api-docs","/webjars/**","/configuration/**","/swagger-resources/**","/swagger-ui/**");
	}
}
