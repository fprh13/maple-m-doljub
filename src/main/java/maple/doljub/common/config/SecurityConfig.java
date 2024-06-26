package maple.doljub.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스 spring security 대상에서 제외
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		requestCache.setMatchingRequestParameterName("null");

        http
                .requestCache(request -> request
                        .requestCache(requestCache))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/",
                                "/login",
                                "/login/process",
                                "/signup", // 회원가입
                                "/signup/process", // 회원 가입 요청
                                "/guild/**", // 길드 정보 조회
                                "/character/search**", // 캐릭터 검색
                                "/character/info/**", // 캐릭터 조회
                                "/error").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers(
                                "/character/**", // 검색 조회 제외한 캐릭터 작업은 로그인한 유저 허용
                                "/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/login/process")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .permitAll()
                );
        http
                .sessionManagement((auth) -> auth
                        // 동시 접속 인원 설정
                        .maximumSessions(1)
                        // 초과 했을 경우
                        // 초과시 새로운 로그인 차단 true
                        // 초과시 기존 세션 하나 삭제 false
                        .maxSessionsPreventsLogin(true));
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());

//         개발 단계에서만 사용할 예정
        http
                .csrf((auth) -> auth.disable())
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                );


        // post가 아닌 get요청으로 로그아웃 진행 할 것
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));


        return http.build();
    }
}
