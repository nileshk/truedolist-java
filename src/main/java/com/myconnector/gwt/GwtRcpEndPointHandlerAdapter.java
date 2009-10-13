package com.myconnector.gwt;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Spring HandlerAdapter to dispatch GWT-RPC requests. Relies on handlers registered by
 * GwtAnnotationHandlerMapper
 * 
 * Solution from: http://blog.digitalascent.com/2007/11/gwt-rpc-with-spring-2x_12.html
 * 
 */
public class GwtRcpEndPointHandlerAdapter extends RemoteServiceServlet implements HandlerAdapter, ServletContextAware {

	static Logger logger = Logger.getLogger(GwtRcpEndPointHandlerAdapter.class); 
	
	private static ThreadLocal handlerHolder = new ThreadLocal();

	private static final long serialVersionUID = -7421136737990135393L;

	private ServletContext servletContext;

	public long getLastModified(HttpServletRequest request, Object handler) {
		return -1;
	}

	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		try {
			// store the handler for retrieval in processCall()
			handlerHolder.set(handler);
			doPost(request, response);
		} finally {
			// clear out thread local to avoid resource leak
			handlerHolder.set(null);
		}

		return null;
	}

	protected Object getCurrentHandler() {
		return handlerHolder.get();
	}

	public boolean supports(Object handler) {
		return handler instanceof RemoteService
				&& handler.getClass().isAnnotationPresent(GwtRpcEndPoint.class);
	}

	@Override
	public String processCall(String payload) throws SerializationException {
		/*
		 * The code below is borrowed from RemoteServiceServet.processCall, with the following
		 * changes:
		 * 
		 * 1) Changed object for decoding and invocation to be the handler (versus the original
		 * 'this')
		 */

		try {
			RPCRequest rpcRequest = RPC.decodeRequest(payload, getCurrentHandler().getClass());

			String retVal = RPC.invokeAndEncodeResponse(getCurrentHandler(),
					rpcRequest.getMethod(), rpcRequest.getParameters());

			return retVal;

		} catch (Throwable t) {
			logger.debug("exception", t);
			return RPC.encodeResponseForFailure(null, t);
		}
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}