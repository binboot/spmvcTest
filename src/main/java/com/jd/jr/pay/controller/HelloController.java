/**
*Copyright © 2015 <copyright holders>
*
*Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”),
*to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/
*or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
*
*The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*
*THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
*WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
*COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
*ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.jd.jr.pay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jd.jr.pay.business.Igreeting;

/** 
 * @Description TODO
 *
 *测试Hello controller层
 * 
 * @author  jiangyoude@jd.com
 * @see 
 * @since   JDK1.6
 */
@Controller
public class HelloController {
	private static final Logger logger=LoggerFactory.getLogger(HelloController.class);
	
	//获取request对象
	@Autowired
	private ServletRequest request;
	
	@Autowired
	private Igreeting ig=null;
	@RequestMapping("/greeting.action")
	public ModelAndView greeting(
			@RequestParam(value="name",defaultValue="anonymous")
			String name)
	{
		System.out.println(request.getLocalAddr());
		Map<String,Object> map=ig.sayHello(name);
		//这里的hello表示跳转到/WEB-INF/views/下的hello.jsp或hello.html或hello.vm页面
		return new ModelAndView("hello",map);
	}
}
