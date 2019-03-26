package com.ganjiangps.huochetou.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ganjiangps.huochetou.common.util.StringUtil;

@Controller
public class IndexController {
	
	
	/*@RequestMapping("/")
	public String index(){
		return "login";
	}*/

	@RequestMapping("/loginJobManager")
	public String login(String username ,String password){
		if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
			return "login";
		}
		if( !"hctmanager".equals(username) || !"Kk@951357new".equals(password) ){
			return "login";
		}
		
		return "JobManager";
	}
	
	/*@RequestMapping("/jobManager")
	public String jobManager(){
		return "JobManager";
	}*/
}
