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

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jd.jr.pay.business.PasswordBus;



/** 
 * @Description TODO
 *
 * 
 * @author  jiangyoude@jd.com
 * @see 
 * @since   JDK1.6
 */
@Controller
public class PayPasswordController {
	private static final Logger logger=LoggerFactory.getLogger(PayPasswordController.class);
	
	//获取request对象
	@Autowired
	private ServletRequest request;

	// 获取response对象
	@Autowired
	private ServletResponse response;
	
	@Autowired
	private PasswordBus pb=null;
	
	@RequestMapping("/isActive.action")
	public ModelAndView isActive(
			@RequestParam(value="pin",defaultValue="anonymous")
			String pin)
	{
		logger.info("请求来自IP:"+request.getLocalAddr());
		Map<String,Object> map=pb.isActive(pin,request.getLocalAddr());
		return new ModelAndView("payPassword",map);
	}
	
}
