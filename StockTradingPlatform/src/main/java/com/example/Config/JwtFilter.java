package com.example.Config;

import com.example.services.servicesImpl.JwtService;
import com.example.services.servicesImpl.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired private JwtService jwtService;

    @Autowired private ApplicationContext context;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authheader=request.getHeader("Authorization");
        String token=null;
        String username=null;

        if(authheader!=null && authheader.contains("Bearer ")){
            token=authheader.substring(7);
            username=jwtService.extractUsername(token);
        }

        /*• The Benefit: Spring Security fetches the user from the database exactly once during the initial authentication phase (via your UserDetailsService).
• By storing that object inside the Authentication token, the user's data travels with the request in memory.
Any controller or service can access it instantly without dragging down database performance.
         */


        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails=context.getBean(UserDetailService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token,userDetails)==true ){
                /*1. The user already proved who they were when they logged in the first time.
                2. Your filter has already validated that the JWT signature is genuine.
                3. Therefore, your application no longer needs or wants the user's raw password in memory. Passing null is a security best-practice to ensure passwords aren't floating around in server memory where they could be exposed.
*/
                /*
                • What it does: This creates a brand new authentication token object. Think of it like printing an official corporate ID badge for the user.
                 */
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
                System.out.println("is Authtoken authenticated : "+authToken.isAuthenticated());
                //This line of code captures web-specific details about the current HTTP request—such as the user's IP address and their session ID—and packages them into a clean object.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }

        filterChain.doFilter(request,response);
        //filterChain.doFilter(request, response); is the command that says: "I am done inspecting this request;
        // pass it along to the next security filter or to the Controller."
            /*As the request travels down the security filter chain, subsequent filters look inside the SecurityContextHolder.
For example, when the request reaches the native UsernamePasswordAuthenticationFilter, its first order of business is to inspect the context:
• If it finds an authentication token (which your jwtFilter placed there), it says, "This request is already authenticated," and immediately passes it along without checking for a password.
• If it finds nothing, it assumes the request is anonymous and proceeds to search for form parameters or login credentials.
*/
    }
}
