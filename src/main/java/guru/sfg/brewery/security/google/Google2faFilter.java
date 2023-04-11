/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.security.google;

import guru.sfg.brewery.domain.security.User;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.stereotype.Component;
/**
 *
 * @author #EM
 */
@Slf4j  
@Component
@RequiredArgsConstructor
public class Google2faFilter extends GenericFilterBean{
        private final  AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
        private final Google2faFailureHandler google2faFailureHandler= new Google2faFailureHandler();
        private final RequestMatcher  urlIs2fa= new AntPathRequestMatcher("/user/verify2fa");
        private final RequestMatcher  urlResource= new AntPathRequestMatcher("/resources/**");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse= (HttpServletResponse) servletResponse;
        
        StaticResourceRequest.StaticResourceRequestMatcher staticResourceRequestMatcher=PathRequest.toStaticResources().atCommonLocations();
        
        if(urlIs2fa.matches(httpServletRequest) || urlResource.matches(httpServletRequest) ||staticResourceRequestMatcher.matcher(httpServletRequest).isMatch()){
        
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication != null && ! authenticationTrustResolver.isAnonymous(authentication)){
        
            if(authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User){
                
                User user =(User) authentication.getPrincipal();
                if(user.getUseGoogle2fa() && user.getGoogle2faRequired()){
                    google2faFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, null);
                    log.debug("2FA Required");
                    return;
                }
            }
        
        }
        
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
    
}
