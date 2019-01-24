package com.technohertz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.model.UserOtp;
import com.technohertz.service.IUserOtpService;
import com.technohertz.util.OtpUtil;

@RestController
@RequestMapping("/otpRest")
public class OtpRestController {
	
	@Autowired
	private IUserOtpService service;
	
	@Autowired
	private OtpUtil util;
	
	
	/**
	 * 2. Save Otp
	 * @param otp
	 * @param errors
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/otp", method = RequestMethod.GET)
	public String saveOTP(@RequestParam("reg_Id") int reg_Id,ModelMap map){
		
			UserOtp otp = new UserOtp();
			otp.setOtp(util.getOTP());
			otp.setReg_Id(reg_Id);
			otp.setCreateDate(getDate());
			otp.setLastModifiedDate(getDate());
			UserOtp userOtp=service.save(otp);
			map.addAttribute("message", "Otp '"+userOtp+"' reset done");
			map.addAttribute("otp", new UserOtp());
		
		return String.valueOf(otp.getOtp());
	}
	
	@RequestMapping(value = "/otp/resend", method = RequestMethod.GET)
	public String getOTP(@RequestParam("reg_Id") int reg_Id,ModelMap map){
		
		UserOtp userOtp = service.getOneByUserId(reg_Id);
			
			if(String.valueOf(userOtp.getOtp())!= null){
					if(getDate().minusMinutes(30).isBefore(userOtp.getCreateDate()) ){
						
						userOtp.getOtp();
						return String.valueOf(userOtp.getOtp());
						
					}else{
						UserOtp saveUserOTP = new UserOtp();
						saveUserOTP.setOtp(util.getOTP());
						saveUserOTP.setOtpId(userOtp.getOtpId());
						saveUserOTP.setReg_Id(reg_Id);
						saveUserOTP.setCreateDate(getDate());
						saveUserOTP.setLastModifiedDate(getDate());
						service.update(saveUserOTP);
						map.addAttribute("message", "Otp '"+saveUserOTP+"' reset done");
						map.addAttribute("otp", String.valueOf(saveUserOTP.getOtp()));
						return String.valueOf(saveUserOTP.getOtp());
					}
				}
				else {
					UserOtp saveUserOTP = new UserOtp();
					saveUserOTP.setOtp(util.getOTP());
					saveUserOTP.setReg_Id(reg_Id);
					saveUserOTP.setCreateDate(getDate());
					saveUserOTP.setLastModifiedDate(getDate());
					UserOtp savedUserOtp=service.save(saveUserOTP);
					map.addAttribute("message", "Otp '"+savedUserOtp+"' reset done");
					map.addAttribute("otp", String.valueOf(savedUserOtp.getOtp()));
					return String.valueOf(savedUserOtp.getOtp());
				}
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

	private LocalDateTime getDate() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now();  
		   
		return now;
		
	}
}
