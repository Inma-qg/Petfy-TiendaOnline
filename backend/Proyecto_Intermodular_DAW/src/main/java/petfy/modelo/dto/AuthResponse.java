package petfy.modelo.dto;

public class AuthResponse {
    private String token;
    private String type;
    private String email;
    private long expiresIn;
    
    
    public AuthResponse() {}
    
    public AuthResponse(String token, String type, String email, long expiresIn) {
        this.token = token;
        this.type = type;
        this.email = email;
        this.expiresIn = expiresIn;
    }
    
    // Getters y Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public long getExpiresIn() {
        return expiresIn;
    }
    
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
