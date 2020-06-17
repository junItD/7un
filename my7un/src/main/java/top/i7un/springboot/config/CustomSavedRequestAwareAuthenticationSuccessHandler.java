package top.i7un.springboot.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Noone on 2020/6/17.
 * 這是一個身份驗證成功策略
 */
@Component
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws ServletException, IOException {
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest,httpServletResponse);
        String url = null;
        if(savedRequest != null){
            url = savedRequest.getRedirectUrl();
        }
        if(url == null){
            getRedirectStrategy().sendRedirect(httpServletRequest,httpServletResponse,"/");
        }
        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    }
    }
