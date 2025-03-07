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
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

/**
 *
 * @author #EM
 */
@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                throws IOException, ServletException {

       HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!requiresAuthentication(request, response)) {
                chain.doFilter(request, response);

                return;
        }

        if (logger.isDebugEnabled()) {
                logger.debug("Request is to process authentication");
        }

        try{
        Authentication authResult = attemptAuthentication(request, response);
              

        if(authResult!=null){
            successfulAuthentication(request, response, chain, authResult);
        }
        else {
        chain.doFilter(req, res);
        }
        }
        catch(AuthenticationException e){
        log.debug("Authorization Failed " + e);
            unsuccessfulAuthentication(request, response, e);
        }
}
    
    
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest hsr, HttpServletResponse hsr1) throws AuthenticationException, IOException, ServletException {
            
            String username=getUsername(hsr);
            String password = getPassword(hsr);
            if(username==null){
            username="";
            }
            if(password==null){
            password="";
            }
            log.debug("Authenticating User : ",username);
        UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(username,password);
       if(!StringUtils.isEmpty(username)){    
           return this.getAuthenticationManager().authenticate(token);
       }
       else 
           return null;
       }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
					+ authResult);
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);

	
	}
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		if (log.isDebugEnabled()) {
			log.debug("Authentication request failed: " + failed.toString(), failed);
			log.debug("Updated SecurityContextHolder to contain null Authentication");
		}
                                        
                                response.sendError(HttpStatus.UNAUTHORIZED.value(), 
                                                HttpStatus.UNAUTHORIZED.getReasonPhrase());

	}
    
    
    private String getUsername(HttpServletRequest hsr) {
        return hsr.getHeader("Api-Key");
    }

    private String getPassword(HttpServletRequest hsr) {
        return hsr.getHeader("Api-Secret");
       // hsr.getParameterValues(string)
    }


    

    
}
