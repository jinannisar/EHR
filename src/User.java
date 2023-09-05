public class User extends HelloApplication{
    private String username;
    private AccessLevel accessLevel;

    public User(){}

    public User(String username, AccessLevel accessLevel){
        this.username=username;
        this.accessLevel=accessLevel;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setAccessLevel(String accessLevel){
        this.accessLevel= AccessLevel.valueOf(accessLevel);
    }
    public AccessLevel getAccessLevel(){
        return accessLevel;
    }
    public enum AccessLevel {
        DOCTOR,
        NURSE,
        PHARMACIST

    }
}
