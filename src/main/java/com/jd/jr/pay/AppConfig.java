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
package com.jd.jr.pay;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.epoll.EpollSocketChannelConfig;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.http.client.Netty4ClientNioHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.jd.jr.pay.business.Igreeting;
import com.jd.jr.pay.business.PasswordBus;
import com.jd.jr.pay.business.impl.GreetingImpl;
import com.jd.jr.pay.business.impl.PasswordBusImpl;
import com.jd.jr.pay.logger.LoggerHandler;

/**
 * @Description annotaion配置类
 *
 * 
 * @author jiangyoude@jd.com
 * @see
 * @since JDK1.6
 */
@Configuration
public class AppConfig {

	@Bean(name = "ig")
	@Scope(value = "prototype")
	public Igreeting ig() {
		return new GreetingImpl();
	}

	@Bean(name = "loggerHandler")
	public LoggerHandler lh() {
		return new LoggerHandler();
	}
	

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		Netty4ClientNioHttpRequestFactory clientHttpRequestFactory=new Netty4ClientNioHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(50);
		clientHttpRequestFactory.setSo_keepalive(false);
		clientHttpRequestFactory.setSo_linger(50);
		clientHttpRequestFactory.setSo_timeout(50);
		return clientHttpRequestFactory;
    }
	
	//ClientHttpRequestFactory 采用HttpComponentsClientHttpRequestFactory,后期改为Netty4ClientHttpRequestFactory
	@Bean(name = "restTemplate")
	public RestTemplate rt() {
		return new RestTemplate(clientHttpRequestFactory());
	}
}
