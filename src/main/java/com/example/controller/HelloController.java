package com.example.controller;

import com.example.service.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

	@Value("${config.WebPurify_hostname_text}")
	private String WebPurify_hostname_text;

	@Value("${config.WebPurify_method_text}")
	private String WebPurify_method_text;

	@Value("${config.WebPurify_api_key_text}")
	private String WebPurify_api_key_text;

	@Value("${config.WebPurify_lang}")
	private String WebPurify_lang;

	@Value("${config.WebPurify_format}")
	private String WebPurify_format;

	@Autowired
	private HelloServiceImpl helloServiceImpl;

	@RequestMapping("/hello")
	public String  hello(){
		return "hello";
	}

	@RequestMapping("/moderate")
	public String  webpurify(){

		String type = "text";
		String str = "fuck";
		String collection = null;
		String id = null;
		String url = WebPurify_hostname_text;
		Map<String,String> map = new HashMap<String,String>();
		map.put("method",WebPurify_method_text);
		map.put("api_key",WebPurify_api_key_text);
		map.put("text",str);
		map.put("lang",WebPurify_lang);
		map.put("format",WebPurify_format);

		return helloServiceImpl.webpurify(map,url,type);
	}

	@RequestMapping("/moderation/callback/webpurify")
	public String  webpurify_image(@RequestParam(value = "status",required = false,defaultValue = "") String status){

		return helloServiceImpl.webpurify_image(status);
	}

	
}
