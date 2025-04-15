package ru.plenkkovii.weather.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.service.SessionService;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();

        String sessionId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SESSION_UUID")) {
                    sessionId = cookie.getValue();
                    Optional<Session> session = sessionService.getSession(UUID.fromString(sessionId));
                    if (session.isPresent()) {
                        System.out.println("session found");
                        request.setAttribute("login", session.get().getUser().getLogin());
                        return true;
                    }
                }
                }
        }

        response.sendRedirect("/index");
        return false;
    }
}
