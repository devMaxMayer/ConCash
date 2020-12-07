package BlinovMS.ConCash.RESTController;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}