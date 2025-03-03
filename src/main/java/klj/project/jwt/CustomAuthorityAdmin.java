package klj.project.jwt;

import org.springframework.security.core.GrantedAuthority;

public class CustomAuthorityAdmin implements GrantedAuthority {
    private final String authority;

    public CustomAuthorityAdmin(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
