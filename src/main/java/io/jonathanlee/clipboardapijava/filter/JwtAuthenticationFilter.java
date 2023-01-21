package io.jonathanlee.clipboardapijava.filter;

import io.jonathanlee.clipboardapijava.service.users.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER_KEY = "Authorization";

  private static final int BEARER_START_INDEX = 7;

  private final JwtService jwtService;

  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    if (request.getRequestURI().startsWith("/auth/login") ||
        request.getRequestURI().startsWith("/register") ||
        request.getRequestURI().startsWith("/password/reset")) {
      log.info("Request with URI: {} bypasses JWT authentication filter", request.getRequestURI());
      filterChain.doFilter(request, response);
      return;
    }
    final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_KEY);
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String jwt = authorizationHeader.substring(BEARER_START_INDEX);
    final String username = this.jwtService.extractUsername(jwt);
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (this.jwtService.isJwtValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }

}
