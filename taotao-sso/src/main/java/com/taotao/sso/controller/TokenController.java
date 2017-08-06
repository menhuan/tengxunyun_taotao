package com.taotao.sso.controller;

import com.taotao.common.pojo.AnswerResult;
import com.taotao.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value="/user/token/{token}", method=RequestMethod.GET)
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		AnswerResult result = tokenService.getUserByToken(token);
		if (StringUtils.isBlank(callback)) {
			return result;
		}
		//支持jsonp调用
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		mappingJacksonValue.setJsonpFunction(callback);
		
		return mappingJacksonValue;
	}
}
