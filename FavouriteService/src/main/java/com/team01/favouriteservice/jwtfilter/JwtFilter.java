package com.team01.favouriteservice.jwtfilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hres = (HttpServletResponse) response;

        String authHeader = hreq.getHeader("authorization");

        if ("OPTIONS".equals(hreq.getMethod())) {
            hres.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        } else {

            try {
                String token = authHeader.split(" ")[1];
                System.out.println(authHeader);
                System.out.println(token);
                Claims claims = Jwts.parser().setSigningKey("secretkey")
                        .parseClaimsJws(token).getBody();
                System.out.println(claims.getIssuer());
                System.out.println(claims.getSubject());
                request.setAttribute("claims", claims);
                chain.doFilter(request, response);

            } catch (Exception e) {
                hres.sendError(401,"Token Expired");
                System.out.println(e.getMessage());
                System.out.println("Access denied");
            }

        }

    }
}
