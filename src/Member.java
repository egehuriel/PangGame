public class Member {
    String username;
    String password;
    int level;
    int point;
    int live;

    public Member(String username, String password){
        this.username = username;
        this.password = password;
        this.level = 1;
        this.point = 0;
        this.live = 3;

    }

    @Override
    public String toString(){
        return username + "," + password + "," + level + "," + point + "," + live;
    }

    public static Member fromString(String string){
    String[] strings = string.split(",");
    Member meber = new Member(strings[0].trim(), strings[1].trim());
    meber.setLevel(Integer.parseInt(strings[2].trim()));
    meber.setPoint(Integer.parseInt(strings[3].trim()));
    meber.setLive(Integer.parseInt(strings[4].trim()));
    return meber;
}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }
}
