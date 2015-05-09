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
package com.jd.jr.pay.pojo;

/** 
 * @Description 通过rest格式查询白条沉睡用户返回的json对应的信息类
 *根据需求现在仅存放在返回json中is30DayConsumer，pin字段
 * 
 * @author  jiangyoude@jd.com
 * @see 
 * @since   JDK1.6
 */
public class QueryPinResult {
	//是否为沉睡用户
	private boolean is30DayConsumer;
	//沉睡用户的pin
	private String pin;
	/**
	 * @return the is30DayConsumer
	 */
	public boolean isIs30DayConsumer() {
		return is30DayConsumer;
	}
	/**
	 * @param is30DayConsumer the is30DayConsumer to set
	 */
	public void setIs30DayConsumer(boolean is30DayConsumer) {
		this.is30DayConsumer = is30DayConsumer;
	}
	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}
	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}
	
}
