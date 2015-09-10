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
package com.jd.jr.pay.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

import com.google.gson.Gson;
import com.jd.jr.pay.business.Igreeting;
import com.jd.jr.pay.business.impl.GreetingImpl;

/**
 * @Description TODO
 *
 * 
 * @author jiangyoude@jd.com
 * @see
 * @since JDK1.6
 */
@Aspect
public class LoggerHandler {

	public static final Logger logger = LoggerFactory
			.getLogger(LoggerHandler.class);

	//@Around("execution(* com.jd.jr.pay.business.Igreeting.*(..)) && @annotation(Calculagraph)")
	@Around("@annotation(Calculagraph)")
	public Object calculagraph(ProceedingJoinPoint pjp) throws Throwable {
		Gson gson=new Gson();
		Object[] args = pjp.getArgs();
		Object target = pjp.getTarget();
		Object thisTarget = pjp.getThis();
		Class thisClass = pjp.getClass();
		// start stopwatch
		logger.info("before @Aspect LoggerHandler calculagraph: \n"
				+ "targetClass:" + target.getClass() + "\n" 
				+ "argsLength:"+ args.length + "\n" 
				+ "class:" + thisClass + "\n"
				+ "thisTarget:" + thisTarget + "\n"
				+ "args:"+ gson.toJson(args) + "\n");

		Object retVal = pjp.proceed();
		// stop stopwatch
		logger.info("after @Aspect LoggerHandler calculagraph");
		return retVal;
	}
}
