package ganisrum.tictactoe.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

public class AuthFilter extends GenericFilterBean {
    private static final String HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";
    private static final List<String> PERMITTED_PATHS = List.of("/auth/signin", "/auth/signup", "/auth/update-access", "/error");

    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;


    public AuthFilter(JwtProvider jwtProvider, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        String path = httpRequest.getRequestURI();

        if (PERMITTED_PATHS.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader(HEADER);

        if (authHeader == null || !authHeader.startsWith(HEADER_PREFIX)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Missing or invalid Authorization header\"}");
            return;
        }
        String token = authHeader.substring(HEADER_PREFIX.length());

        if (!jwtProvider.validateAccessToken(token)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Invalid ot expired token\"}");
            return;
        }

        Claims claims = jwtProvider.getAccessClaims(token);
        JwtAuthentication authentication = jwtUtil.createJwtAuthentication(claims);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
