package om.auth.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import om.auth.library.constant.HeaderConstants;

@Component
public class UserAuditInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {
        String createdById =
                StringUtils.isNotEmpty(request.getHeader(HeaderConstants.HEADER_CREATED_BY_ID))
                        ? request.getHeader(HeaderConstants.HEADER_CREATED_BY_ID)
                        : "-1";
        String modifiedById =
                StringUtils.isNotEmpty(request.getHeader(HeaderConstants.HEADER_MODIFIED_BY_ID))
                        ? request.getHeader(HeaderConstants.HEADER_MODIFIED_BY_ID)
                        : "-1";
        String createdBy =
                StringUtils.isNotEmpty(request.getHeader(HeaderConstants.HEADER_CREATED_BY_NAME))
                        ? request.getHeader(HeaderConstants.HEADER_CREATED_BY_NAME)
                        : "System";
        String modifiedBy =
                StringUtils.isNotEmpty(request.getHeader(HeaderConstants.HEADER_MODIFIED_BY_NAME))
                        ? request.getHeader(HeaderConstants.HEADER_CREATED_BY_NAME)
                        : "System";

        UserContext.setUserContext(Long.valueOf(createdById), Long.valueOf(modifiedById), createdBy,
                modifiedBy);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {
        UserContext.clear();
    }

}
