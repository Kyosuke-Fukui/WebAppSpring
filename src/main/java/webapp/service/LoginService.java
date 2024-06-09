package webapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import webapp.entity.AuthUser;
import webapp.form.LoginForm;
import webapp.repository.UserInfoRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final UserInfoRepository repository;
	private final PasswordEncoder passwordEncoder;

	public void register(LoginForm form){
		var user = new AuthUser();
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setUsername(form.getUsername());
		user.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		repository.save(user);
	}
	
	public Optional<AuthUser> findByname(String username) {
		return repository.findByUsername(username);
	}

	public boolean updatePassword(String username, String password) {
		Optional<AuthUser> optionalUser = repository.findByUsername(username);
		if (optionalUser.isPresent()) {
			AuthUser user = optionalUser.get();
			user.setPassword(passwordEncoder.encode(password));
			user.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			repository.save(user);
			return true;
		}
		return false;
	}
}
