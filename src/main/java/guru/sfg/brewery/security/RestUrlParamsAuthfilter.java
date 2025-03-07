/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guru.sfg.brewery.security;


import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 *
 * @author #EM
 */
@Slf4j
public class RestUrlParamsAuthfilter  extends  AbstractAuthenticationProcessingFilter{

    public RestUrlParamsAuthfilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest= (HttpServletRequest) req;
            HttpServletResponse httpServletResponse= (HttpServletResponse) res;

            if(!requiresAuthentication(httpServletRequest, httpServletResponse)){
                    chain.doFilter(httpServletRequest, httpServletResponse);
                    return;
              }
     if(!logger.isDebugEnabled()){
         log.debug("Request is to process authentication");
     }

    try{
            Authentication authResult= attemptAuthentication(httpServletRequest, httpServletResponse);
                
            
            if(authResult!=null){
                successfulAuthentication(httpServletRequest, httpServletResponse, chain,authResult);
    }
    else{
                 chain.doFilter(req, res);
            }
    }
    catch(AuthenticationException e){
        unsuccessfulAuthentication(httpServletRequest, httpServletResponse, e);
    }
    
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
SecurityContextHolder.clearContext();

		if (log.isDebugEnabled()) {
			log.debug("Authentication request failed: " + failed.toString(), failed);
			log.debug("Updated SecurityContextHolder to contain null Authentication");
		}

response.sendError(HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
			log.debug("Authentication success. Updating SecurityContextHolder to contain: "
					+ authResult);
		}
        SecurityContextHolder.getContext().getAuthentication();
    }
    
    
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
           
                String username=getUsername(request);
                String password = getPassword(request);
                
                if(username==null){
                    username="";
                }
                
                if(password==null){
                     password="";
                   }
                log.debug("Username = "+ username);
                log.debug("Password = "+ password);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
              
                return this.getAuthenticationManager().authenticate(token);
    }

    private String getUsername(HttpServletRequest request) {
        
        return request.getParameter("Api-Key");
    }

    private String getPassword(HttpServletRequest request) {
        
        return request.getParameter("Api-Secret");   
    }
    
    }

