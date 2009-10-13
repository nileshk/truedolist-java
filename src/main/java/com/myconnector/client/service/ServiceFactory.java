package com.myconnector.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.myconnector.client.ClientConstants;

public class ServiceFactory implements IServiceFactory {

    private UserServiceGwtAsync userService;
    private TodoServiceGwtAsync todoService;

    public TodoServiceGwtAsync getTodoService() {
        return todoService;
    }

    public UserServiceGwtAsync getUserService() {
        return userService;
    }

    public static void setRpcEndPointUrl(Object proxy) {
        ServiceDefTarget target = (ServiceDefTarget) proxy;

        StringBuffer sb = new StringBuffer();
        sb.append(proxy.getClass().getName());
        String endPointName = sb.substring(0, sb.indexOf("_Proxy"));
        endPointName = endPointName.replace('.', '/');
        String url = ClientConstants.SERVLET_NAME + "/gwt-rpc/" + endPointName + ".do";
        GWT.log("Setting url: " + url, null);
        target.setServiceEntryPoint(url);
    }

    private String getUrl(String serviceName) {
        String moduleRelativeURL = GWT.getModuleBaseURL() + serviceName;
        GWT.log(moduleRelativeURL, null);
        return moduleRelativeURL;
    }

    public ServiceFactory() {
        GWT.log("Initializing ServiceFactory...", null);
        userService = (UserServiceGwtAsync) GWT.create(UserServiceGwt.class);
        setRpcEndPointUrl(userService);
        todoService = (TodoServiceGwtAsync) GWT.create(TodoServiceGwt.class);
        setRpcEndPointUrl(todoService);
        GWT.log("Finished initializing ServiceFactory...", null);
    }

}
