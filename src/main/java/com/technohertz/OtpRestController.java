package com.technohertz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.model.UserOtp;
import com.technohertz.service.IUserOtpService;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.OtpUtil;

@RestController
@RequestMapping("/otpRest")
public class OtpRestController {
	
	@Autowired
	private IUserOtpService service;
	
	@Autowired
	private OtpUtil util;
	
	@Autowired
	private IUserRegisterService userRegisterService;

	
	/**
	 * 2. Save Otp
	 * @param otp
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/reset/{userId}")
	public String save(@PathVariable("userId") int userId,BindingResult errors,ModelMap map){
		if(errors.hasErrors()){
			map.addAttribute("userId", userId);
		}else{
			UserOtp otp = new UserOtp();
			otp.setOtp(util.getOTP());
			UserOtp userOtp=service.save(otp);
			map.addAttribute("message", "Otp '"+userOtp+"' reset done");
			map.addAttribute("otp", new UserOtp());
		}
		return "OtpRegister";
	}
	

	
	/**
	 * 3. Get All Otps
	 * @param map
	 * @return
	 */
	@GetMapping("/getAllOtps")
	public String getAll(ModelMap map){
		List<UserOtp> list=service.getAll();
		map.addAttribute("otpsList", list);
		return "OtpData";
	}
	
	/**
	 * 4. Delete Otp by Id
	 * @param otpId
	 * @return
	 */
	@GetMapping("/deleteOtp")
	public String delete(@RequestParam int userId){
		service.deleteById(userId);
		return "redirect:getAllOtps";
	}
	
	/**
	 * 5. Show Edit Page
	 * @param otpId
	 * @param map
	 * @return
	 */
	@GetMapping("/editOtp")
	public String edit(@RequestParam int otpId,ModelMap map){
		map.addAttribute("otp", service.getOneById(otpId));
		return "OtpDataEdit";
	}
	
	/**
	 * 6. Update Otp
	 * @param otp
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/updateOtp")
	public String update(@ModelAttribute UserOtp otp,BindingResult errors,ModelMap map){
		String page=null;
		if(errors.hasErrors()){
			map.addAttribute("otp", otp);
			page="OtpDataEdit";
		}else{
			service.update(otp);
			map.addAttribute("otp", new UserOtp());
			page="redirect:getAllOtps";
		}
		return page;
	}

}
