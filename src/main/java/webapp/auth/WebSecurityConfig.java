package webapp.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	/* @Configurationとセットで@Beanを付与したメソッドはDIコンテナに
	 * 登録され注入（インスタンス化）可能 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {

			@Override
			public String encode(CharSequence rawPassword) {
				try {
					MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
					byte[] digest = messageDigest.digest(rawPassword.toString().getBytes());
					return bytesToHex(digest);
				} catch (NoSuchAlgorithmException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {

				//		    	System.out.println(rawPassword);
				//		    	System.out.println(encode(rawPassword));
				//		    	System.out.println(encodedPassword);
				return encode(rawPassword).equals(encodedPassword);
			}

			//16進数表記
			private String bytesToHex(byte[] bytes) {
				StringBuilder hexString = new StringBuilder(2 * bytes.length);
				for (byte b : bytes) {
					String hex = Integer.toHexString(0xff & b);
					if (hex.length() == 1) {
						hexString.append('0');
					}
					hexString.append(hex);
				}
				return hexString.toString();
			}
		};
	}

	//認証後遷移先、認証前アクセス拒否ページを設定
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login.loginPage("/login") //ログイン画面をlogin.htmlに設定
				.defaultSuccessUrl("/main"))
				.authorizeHttpRequests(
						authz -> authz.requestMatchers("/login", "/signup", "/login?error", "/css/**").permitAll()
								.anyRequest().authenticated());

		return http.build();
	}
}
