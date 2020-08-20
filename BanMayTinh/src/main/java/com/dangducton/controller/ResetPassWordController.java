package com.dangducton.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dangducton.entities.ConfirmationToken;
import com.dangducton.entities.Nguoidung;
import com.dangducton.repository.ConfirmationTokenRepository;
import com.dangducton.service.EmailSenderService;
import com.dangducton.service.NguoiDungServiceImpl;

@Controller
public class ResetPassWordController {

	@Autowired
	private NguoiDungServiceImpl nguoiDungServiceImpl;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public String displayResetPassword(Model model, Nguoidung user) {
		model.addAttribute("user", user);
		return "frontend/forgotPassword";
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public String forgotUserPassword(Model model, Nguoidung user, HttpServletRequest request) {
		Nguoidung existingUser = nguoiDungServiceImpl.findByEmail(user.getEmail());
		if (existingUser != null) {
			ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

			confirmationTokenRepository.save(confirmationToken);

			String url = request.getRequestURL().toString();
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Hoàn thành thiết lập lại mật khẩu!");
			mailMessage.setText("Để hoàn tất quá trình đặt lại mật khẩu, xin vui lòng bấm vào đây: " + url
					+ "/confirm-reset?token=" + confirmationToken.getConfirmationToken());
			emailSenderService.sendEmail(mailMessage);
			model.addAttribute("message",
					"Yêu cầu đặt lại mật khẩu đã thực hiện được. Kiểm tra Email để lấy liên kết đặt lại mật khẩu.");
			return "frontend/dangnhap";
		} else {
			model.addAttribute("message", "Email không tồn tại!");
			return "frontend/error";
		}
	}

	@RequestMapping(value = "/forgot-password/confirm-reset", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			Nguoidung user = nguoiDungServiceImpl.findByEmail(token.getUser().getEmail());
			user.setStatus(1);
			nguoiDungServiceImpl.save(user);
			modelAndView.addObject("user", user);
			modelAndView.addObject("email", user.getEmail());
			modelAndView.setViewName("frontend/resetPassword");
		} else {
			modelAndView.addObject("message", "Liên kết không hợp lệ hoặc bị lỗi!");
			modelAndView.setViewName("frontend/error");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ModelAndView resetUserPassword(ModelAndView modelAndView, Nguoidung user) {
		// ConfirmationToken token =
		// confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (user.getEmail() != null) {
			// use email to find user
			Nguoidung tokenUser = nguoiDungServiceImpl.findByEmail(user.getEmail());
			tokenUser.setStatus(1);
			tokenUser.setPassword(user.getPassword());
			// System.out.println(tokenUser.getPassword());
			nguoiDungServiceImpl.save(tokenUser);
			modelAndView.addObject("message",
					"Đặt lại mật khẩu thành công. Bây giờ bạn có thể đăng nhập với mật khẩu mới.");
			modelAndView.setViewName("frontend/dangnhap");
		} else {
			modelAndView.addObject("message", "Liên kết không hợp lệ hoặc bị lỗi!");
			modelAndView.setViewName("frontend/error");
		}

		return modelAndView;
	}

	public ConfirmationTokenRepository getConfirmationTokenRepository() {
		return confirmationTokenRepository;
	}

	public void setConfirmationTokenRepository(ConfirmationTokenRepository confirmationTokenRepository) {
		this.confirmationTokenRepository = confirmationTokenRepository;
	}

	public EmailSenderService getEmailSenderService() {
		return emailSenderService;
	}

	public void setEmailSenderService(EmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}
}
