package com.charniuk.authservice.security;

import com.charniuk.authservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String BEARER_PREFIX = "Bearer ";
  public static final String HEADER_NAME = "Authorization";
  private final JwtService jwtService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    var authHeader = request.getHeader(HEADER_NAME);
    if (StringUtils.isEmpty(authHeader) || !Strings.CS.startsWith(authHeader, BEARER_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }
    var jwt = authHeader.substring(BEARER_PREFIX.length());
    var username = jwtService.extractUsername(jwt);

    if (StringUtils.isNotEmpty(username)
        && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userService
          .userDetailsService()
          .loadUserByUsername(username);

      if (jwtService.isTokenValid(jwt, userDetails)) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
      }
    }
    filterChain.doFilter(request, response);
  }
}
