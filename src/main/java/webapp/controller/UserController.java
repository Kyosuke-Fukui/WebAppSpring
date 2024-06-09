package webapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import webapp.form.LoginForm;
import webapp.service.LoginService;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final LoginService service;

	@GetMapping("/login")
	public String login(Model model, LoginForm form) {
		return "login";
	}

	@GetMapping(value = "/login", params = "error")
	public String loginErr(Model model, LoginForm form) {
		return "login_err";
	}

	@GetMapping("/main")
	public String view() {
		return "main";
	}

	@GetMapping("/signup")
	public String showRegisterForm(Model model, LoginForm form) {
		return "register";
	}

	@PostMapping("/signup")
	public String createAccount(Model model, LoginForm form) {
		String username = form.getUsername();
		String password = form.getPassword();

		if ((username == null || username.isBlank()) ||
				(password == null || password.isBlank())) {
			model.addAttribute("msg", "ユーザ名またはパスワードが入力されていません");
			return "register";
		}

		service.register(form);
		return "register";
	};

	@GetMapping("/updateUserInfo")
	public String showUpdateUserInfoForm(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("username", user.getUsername());
		return "update_user_info";
	}

	@PostMapping("/updateUserInfo")
	public String updateUserInfo(@RequestParam("pass") String password, Model model,
			@AuthenticationPrincipal User user) {

		model.addAttribute("username", user.getUsername());

		if (password == null || password.isEmpty()) {
			model.addAttribute("msg", "パスワードを入力してください。");
			return "update_user_info";
		}

		boolean success = service.updatePassword(user.getUsername(), password);
		if (success) {
			model.addAttribute("msg", "ユーザ情報を更新しました。");
		} else {
			model.addAttribute("msg", "パスワード更新に失敗しました。");
		}

		return "update_user_info";
	}

	//	private final PasswordEncoder passwordEncorder;
	//
	//	//Spring Securityを使わない場合	
	//	@PostMapping("/login")
	//	public String login(LoginForm form) {
	//		//認証処理
	//		var userInfo = service.findByName(form.getUserName());
	//		var isCollectAuth = userInfo.isPresent() &&
	//				passwordEncorder.matches(form.getPassword(), userInfo.get().getPassword());
	//
	//		if (isCollectAuth) {
	//			return "main";
	//		} else {
	//			return "login";
	//		}
	//	};

}
