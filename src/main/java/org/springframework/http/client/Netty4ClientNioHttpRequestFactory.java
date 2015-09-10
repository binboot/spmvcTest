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
package org.springframework.http.client;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;

/**
 * @Description TODO
 *
 * 
 * @author jiangyoude@jd.com
 * @see
 * @since JDK1.6
 */
public class Netty4ClientNioHttpRequestFactory implements
		ClientHttpRequestFactory, AsyncClientHttpRequestFactory,
		InitializingBean, DisposableBean {

	/**
	 * The default maximum request size.
	 * 
	 * @see #setMaxRequestSize(int)
	 * @deprecated
	 */
	@Deprecated
	public static final int DEFAULT_MAX_REQUEST_SIZE = 1024 * 1024 * 10;

	/**
	 * The default maximum response size.
	 * 
	 * @see #setMaxResponseSize(int)
	 */
	public static final int DEFAULT_MAX_RESPONSE_SIZE = 1024 * 1024 * 10;

	private final EventLoopGroup eventLoopGroup;

	private final boolean defaultEventLoopGroup;

	private int maxRequestSize = DEFAULT_MAX_REQUEST_SIZE;

	private int maxResponseSize = DEFAULT_MAX_REQUEST_SIZE;

	private SslContext sslContext;

	private volatile Bootstrap bootstrap;
	
	private Integer connectTimeout=-1;
	
	private Integer so_timeout=-1;
	
	private Boolean  so_keepalive=false; 

	private Integer so_linger=-1;



	/**
	 * Create a new {@code Netty4ClientHttpRequestFactory} with a default
	 * {@link NioEventLoopGroup}.
	 */
	public Netty4ClientNioHttpRequestFactory() {
		int ioWorkerCount = Runtime.getRuntime().availableProcessors() * 2;
		this.eventLoopGroup = new NioEventLoopGroup(ioWorkerCount);
		this.defaultEventLoopGroup = true;
	}

	/**
	 * Create a new {@code Netty4ClientHttpRequestFactory} with the given
	 * {@link EventLoopGroup}.
	 * <p>
	 * <b>NOTE:</b> the given group will <strong>not</strong> be
	 * {@linkplain EventLoopGroup#shutdownGracefully() shutdown} by this
	 * factory; doing so becomes the responsibility of the caller.
	 */
	public Netty4ClientNioHttpRequestFactory(EventLoopGroup eventLoopGroup) {
		Assert.notNull(eventLoopGroup, "EventLoopGroup must not be null");
		this.eventLoopGroup = eventLoopGroup;
		this.defaultEventLoopGroup = false;
	}

	/**
	 * Set the default maximum request size.
	 * <p>
	 * By default this is set to {@link #DEFAULT_MAX_REQUEST_SIZE}.
	 * 
	 * @see HttpObjectAggregator#HttpObjectAggregator(int)
	 * @deprecated as of 4.1.5 this property is no longer supported; effectively
	 *             renamed to {@link #setMaxResponseSize(int)}.
	 */
	@Deprecated
	public void setMaxRequestSize(int maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}

	/**
	 * Set the default maximum response size.
	 * <p>
	 * By default this is set to {@link #DEFAULT_MAX_RESPONSE_SIZE}.
	 * 
	 * @see HttpObjectAggregator#HttpObjectAggregator(int)
	 * @since 4.1.5
	 */
	public void setMaxResponseSize(int maxResponseSize) {
		this.maxResponseSize = maxResponseSize;
	}

	/**
	 * Set the SSL context. When configured it is used to create and insert an
	 * {@link io.netty.handler.ssl.SslHandler} in the channel pipeline.
	 * <p>
	 * By default this is not set.
	 */
	public void setSslContext(SslContext sslContext) {
		this.sslContext = sslContext;
	}

	private Bootstrap getBootstrap() {
		if (this.bootstrap == null) {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(this.eventLoopGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
					.option(ChannelOption.SO_KEEPALIVE, so_keepalive)
					.option(ChannelOption.SO_LINGER, so_linger)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel channel)
								throws Exception {
							ChannelPipeline pipeline = channel.pipeline();
							if (sslContext != null) {
								pipeline.addLast(sslContext.newHandler(channel
										.alloc()));
							}
							pipeline.addLast(new HttpClientCodec());
							pipeline.addLast(new HttpObjectAggregator(
									maxResponseSize));
						}
					});
			this.bootstrap = bootstrap;
		}
		return this.bootstrap;
	}

	/**
	 * @return the connectTimeout
	 */
	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the so_timeout
	 */
	public Integer getSo_timeout() {
		return so_timeout;
	}

	/**
	 * @param so_timeout the so_timeout to set
	 */
	public void setSo_timeout(Integer so_timeout) {
		this.so_timeout = so_timeout;
	}

	/**
	 * @return the so_keepalive
	 */
	public Boolean getSo_keepalive() {
		return so_keepalive;
	}

	/**
	 * @param so_keepalive the so_keepalive to set
	 */
	public void setSo_keepalive(Boolean so_keepalive) {
		this.so_keepalive = so_keepalive;
	}

	/**
	 * @return the so_linger
	 */
	public Integer getSo_linger() {
		return so_linger;
	}

	/**
	 * @param so_linger the so_linger to set
	 */
	public void setSo_linger(Integer so_linger) {
		this.so_linger = so_linger;
	}

	/**
	 * @return the sslContext
	 */
	public SslContext getSslContext() {
		return sslContext;
	}

	@Override
	public void afterPropertiesSet() {
		getBootstrap();
	}

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod)
			throws IOException {
		return createRequestInternal(uri, httpMethod);
	}

	@Override
	public AsyncClientHttpRequest createAsyncRequest(URI uri,
			HttpMethod httpMethod) throws IOException {
		return createRequestInternal(uri, httpMethod);
	}

	private Netty4ClientHttpRequest createRequestInternal(URI uri,
			HttpMethod httpMethod) {
		return new Netty4ClientHttpRequest(getBootstrap(), uri, httpMethod);
	}
	


	@Override
	public void destroy() throws InterruptedException {
		if (this.defaultEventLoopGroup) {
			// Clean up the EventLoopGroup if we created it in the constructor
			this.eventLoopGroup.shutdownGracefully().sync();
		}
	}
}
