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
package com.jd.jr.pay.business.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.reflect.TypeToken;
import com.jd.jr.pay.business.Igreeting;
import com.jd.jr.pay.pojo.QueryPin;
import com.jd.jr.pay.pojo.QueryPinResult;
import com.jd.payment.paycommon.utils.GsonUtils;

/**
 * @Description TODO
 *
 * 
 * @author jiangyoude@jd.com
 * @see
 * @since JDK1.6
 */
public class GreetingImpl implements Igreeting {

	private static final Logger log = LoggerFactory
			.getLogger(GreetingImpl.class);
	

	@Autowired
    private RestTemplate restTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jd.jr.pay.business.Igreeting#sayHello(java.lang.String)
	 */
	@Override
	public Map<String, Object> sayHello(String name) {
		
		
		String pin=name;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 生成查询的json
		QueryPin qp = new QueryPin(pin);
		String requestJson = GsonUtils.toJson(qp);
		// 白条沉睡用户地址
		final String uri = "http://front.baitiao.jd.local/service/loan/queryBtFor30Day";
		log.info("向地址" + uri + "查询用户30天内是否使用过白条,请求参数为:"
				+ GsonUtils.toJson(qp) + "");

		// 设置请求头中Content-Type为application/json
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//设置请求头中Connection为close，用于启用无状态短链接，保护白条服务端socket资源支持更多的客户端链接
		headers.set("Connection", "close");
		// 设置请求实体
		HttpEntity<String> entity = new HttpEntity<String>(requestJson,
				headers);
		// 发送请求
		ResponseEntity<String> result = restTemplate.exchange(uri,
				HttpMethod.POST, entity, String.class);
		
		log.info("向地址" + uri + "查询用户30天内是否使用过白条,反馈参数为:"
				+ result.getBody()+ "");
		QueryPinResult qpr = (QueryPinResult) GsonUtils.fromJson(
				result.getBody(), new TypeToken<QueryPinResult>() {}.getType());
		map.put("userName", name);
		return map;
	}

}
