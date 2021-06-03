package com.dream.have.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 환경설정 파일로 인식
@EnableWebSecurity // 환경설정 중 Security 관련임을 명시(레거시 때는 Security-context.xml에 해줬던 설정들)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 미리 혹은 후에 security 체크 할건지
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter에 Security가 구현되어 있음
//	@Bean // 외부에서 만들어진 객체 만들 때, component -> 내가 만드는 객체를 만들 때?
//	public BCryptPasswordEncoder encodePwd() {
//		return new BCryptPasswordEncoder();
//	}
	
	/* Authentication 로그인, Authroization 권한 */
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 보안 비활성화
			.authorizeRequests()
				.antMatchers("/", "/account/register", "/css/**", "/api/**").permitAll() // root page는 누구나 접근 가능
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/account/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder())
			.usersByUsernameQuery("select username, password, enabled " // 인증처리
				+ "from user "
				+ "where username = ?")
			.authoritiesByUsernameQuery("select u.username, r.name " // 권한처리
				+ "from user_role ur inner join user u on ur.user_id = u.id "
				+ "inner join role r on ur.role_id = r.id "
				+ "where u.username = ?");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
