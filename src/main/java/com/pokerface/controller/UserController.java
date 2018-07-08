package com.pokerface.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pokerface.config.WebSecurityConfig;
import com.pokerface.model.Exchange;
import com.pokerface.model.User;
import com.pokerface.service.UserService;
import com.pokerface.util.HttpUtil;

@Controller
public class UserController {
    
	@Autowired
    private UserService userService;
	
	private final static String SMS_CODE = "smdCode";
	
	@RequestMapping("/register")
    public String register(Map<String, Object> model) {
		model.put("user", new User());
        return "register";
    }
	
	@RequestMapping("/getSmsCode")
	@ResponseBody
    public Map<String, Object> sendSms(String phone, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//随机生成六位数验证码
		Random random = new Random();
		int number = random.nextInt(8998) + 1001;
		String code = Integer.toString(number);

		String url = "https://www.ginota.com/gemp/sms/json?apiKey=43WIbLdMBf0M3pG5whCGII4zMf2SQEcH&apiSecret=%23%24%2377Z%40qL%25&srcAddr=MyCompany&dstAddr=" + phone + "&content=短信验证码是" + code;
		String res = HttpUtil.doPost(url, "");
		System.out.println("send sms result:"+res + ",code:"+code);
		map.put("success", false);
		try {
			JSONObject result = new JSONObject(res);
			String status = result.getString("status");
			if("0".equals(status)){
				map.put("success", true);
				session.setAttribute(SMS_CODE, code);
			} else {
				map.put("message", "发送短信验证码失败，原因："+result.getString("desc"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			map.put("message", "发送短信验证码失败，请稍后再试");
		}
		
        return map;
    }
	
	@PostMapping("/checkUser")
    @ResponseBody
    public Map<String, Object> checkUser(String phone, HttpSession session) {
    	Map<String, Object> map = new HashMap<>();
    	map.put("phoneExists", userService.phoneExists(phone));
    	return map;
    }
	
	@RequestMapping("/registerSubmit")
	@ResponseBody
    public Map<String, Object> registerSubmit(String phone, String pwd, String name, String weChat, String smsCode, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = (String) session.getAttribute(SMS_CODE);
		if(code == null || !code.equals(smsCode)){
			map.put("success", false);
			map.put("message", "短信验证码错误");
            return map;
		}
		User user = new User();
		user.setName(name);
		user.setPhone(phone);
		user.setPwd(pwd);
		user.setWeChat(weChat);
		user.setCreateTime(new Date());
		userService.saveUser(user);
		map.put("success", true);
		map.put("message", "注册成功");
        return map;
    }
	
    @RequestMapping("/exgs")
    public String exchangeList(Map<String, Object> model, HttpSession session) {
    	User user = (User) session.getAttribute(WebSecurityConfig.LOGIN_USER);
    	model.put("exchanges", userService.findExchange(user.getPhone()));
        return "exchanges";
    }
    
    @RequestMapping("/addExg")
    public String toAddExg(Map<String, Object> model, HttpSession session) {
    	model.put("operType", "add");
        model.put("exg", new Exchange());
        return "exgDetail";
    }
    
    @RequestMapping("/addExgSubmit")
    public String addExg(Model model, @Valid @ModelAttribute Exchange exg, Boolean buy, BindingResult bindingResult, HttpSession session) {
    	if (bindingResult.hasErrors()) {
        	model.addAttribute("operType", "add");
            return "exgDetail";
        }
    	User user = (User) session.getAttribute(WebSecurityConfig.LOGIN_USER);
    	exg.setPhone(user.getPhone());
    	exg.setCreateTime(new Date());
    	exg.setBuy(buy);
    	userService.saveExg(exg);
        return "redirect:/exgs";
    }
    
    @RequestMapping("/endExg")
    public String endExg(Long id, HttpSession session) {
    	userService.endExchange(id);
        return "redirect:/exgs";
    }

}
