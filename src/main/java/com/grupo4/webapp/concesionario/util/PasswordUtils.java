@Component
public class PasswordUtils {
    public String encryptedPassword(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
    
    public boolean checkPassword(String pass, String encryptedPass){
        return BCrypt.checkpw(pass, encryptedPass);
    }
}
