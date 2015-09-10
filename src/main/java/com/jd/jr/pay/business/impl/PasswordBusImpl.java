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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.jr.pay.business.PasswordBus;
import com.jd.jr.pay.logger.Calculagraph;
import com.jd.jr.pay.pojo.QueryPinResult;

/**
 * @Description TODO
 *
 * 
 * @author jiangyoude@jd.com
 * @see
 * @since JDK1.6
 */
public class PasswordBusImpl implements PasswordBus {

	private static final Logger log = LoggerFactory
			.getLogger(PasswordBusImpl.class);

	/**
	 * rest请求模板
	 */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 获取支付密码服务的基础uri
	 */
	private String basicUri = null;
	/**
	 * 由支付密码系统分配的 调用者id测试环境是testApp,线上需要申请
	 */
	private String caller = null;
	/**
	 * 由支付密码系统分配的调用者密钥 测 试环境是testApp ,线上需要申请
	 */
	private String signature = null;
	/**
	 * 支付密码所属系统,京东商城为1, 其他业务由由支付密码系统分配
	 */
	private int systemNo = 1;
	/**
	 * 支付密码所属系统的业务规则,京东商城为1 其他业务由由支付密码系统分配
	 */
	private int sysBizType = 1;
	/**
	 * 支付密码所属系统的业务二级规则 ,京东商城为1 其他业务由由支付密码系统分配
	 */
	private int bizType = 1;
	
	
	/**
	 * 存放rest请求头，包括: Content-Type Accept-Charset Connection 等
	 */
	private Map<String, String> restHeaders = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jd.jr.pay.business.PasswordBus#isActive(java.lang.String)
	 */
	@Override
	@Calculagraph
	public Map<String, Object> isActive(String pin, String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		String reqeustBody="";

		String isActiveUri = basicUri + "isActive";
		String requestJson = gson.toJson(reqeustBody);
		log.info("向地址:" + isActiveUri + "查询用户pin:" + pin + "的支付密码是否已激活,请求参数为:"
				+ requestJson + "");
		// 设置请求头中Content-Type为application/json
		HttpHeaders headers = new HttpHeaders();

		if (null != restHeaders) {
			headers.setAll(restHeaders);
		} else {
			// 设置请求头为application/json;charset=utf-8
			headers.set("Content-Type", "application/json;charset=utf-8");
			// 设置可接收的返回编码为utf-8
			headers.set("Accept-Charset", "utf-8");
			// 设置请求头中Connection为close，用于启用无状态短链接，保护白条服务端socket资源支持更多的客户端链接
			headers.set("Connection", "close");
		}

		// 设置请求实体
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		// 发送请求
		ResponseEntity<String> result = restTemplate.exchange(isActiveUri,
				HttpMethod.POST, entity, String.class);
		log.info("向地址:" + isActiveUri + "查询用户pin:" + pin + "的支付密码是否已激活,反馈参数为:"
				+ result.getBody() + "");
		
		String responseBody=result.getBody();
		map.put("pin", responseBody);
		return map;
	}

	/**
	 * @return the restTemplate
	 */
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	/**
	 * @param restTemplate
	 *            the restTemplate to set
	 */
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * @return the basicUri
	 */
	public String getBasicUri() {
		return basicUri;
	}

	/**
	 * @param basicUri
	 *            the basicUri to set
	 */
	public void setBasicUri(String basicUri) {
		this.basicUri = basicUri;
	}

	/**
	 * @return the caller
	 */
	public String getCaller() {
		return caller;
	}

	/**
	 * @param caller
	 *            the caller to set
	 */
	public void setCaller(String caller) {
		this.caller = caller;
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param signature
	 *            the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @return the systemNo
	 */
	public int getSystemNo() {
		return systemNo;
	}

	/**
	 * @param systemNo
	 *            the systemNo to set
	 */
	public void setSystemNo(int systemNo) {
		this.systemNo = systemNo;
	}

	/**
	 * @return the sysBizType
	 */
	public int getSysBizType() {
		return sysBizType;
	}

	/**
	 * @param sysBizType
	 *            the sysBizType to set
	 */
	public void setSysBizType(int sysBizType) {
		this.sysBizType = sysBizType;
	}

	/**
	 * @return the bizType
	 */
	public int getBizType() {
		return bizType;
	}

	/**
	 * @param bizType
	 *            the bizType to set
	 */
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}




	/**
	 * @return the restHeaders
	 */
	public Map<String, String> getRestHeaders() {
		return restHeaders;
	}




	/**
	 * @param restHeaders the restHeaders to set
	 */
	public void setRestHeaders(Map<String, String> restHeaders) {
		this.restHeaders = restHeaders;
	}

}
