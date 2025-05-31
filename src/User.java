import java.io.*;
import java.util.*;
public class User {
    List<Member> users = new ArrayList<>();
    List<Member> highscore = new ArrayList<>(users);
    void load(String filep) throws IOException{
        users.clear();
        BufferedReader reader = new BufferedReader(new FileReader(filep));
        try{
            String string;
            while((string = reader.readLine()) != null){
                users.add(Member.fromString(string));
            }
        }catch(IOException e ){System.err.println("hata reader");}
        finally{
            reader.close();
        }

    }
    void save(String filep) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filep));
        try{
            for(Member member : users){
            writer.write(member.toString());
            writer.newLine();
         }   
        }catch(IOException e) {System.err.println("hata writer");}
        finally{
            writer.close();  
        }      
        
    }
    boolean register(String username, String password) throws IOException{
        for(Member member1 : users){
            if(member1.getUsername().equals(username)){
                return false;
            }
        }       
        Member member2 = new Member(username, password);
        users.add(member2);
        BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true));
        try{
            writer.write(member2.toString());
            writer.newLine();
        }catch(IOException e) {System.err.println("hata kayıt");}
        finally{
            writer.close();
        }
        return true;
    }
    Member login(String username, String password){
        for(Member member3 : users){
            if(member3.getUsername().equals(username) && member3.getPassword().equals(password)){
                return member3;
            }
        }
        return null;
    }
    Member getuser(String username){
        for(Member member4 : users){
            if(member4.getUsername().equals(username)){
                return member4;
            }
        }
        return null;
    }
    void updateuser(Member member){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(member.getUsername())){
                users.set(i, member);
                break;
            }
        }
    }
    List highscore(int count){
        List<Member> sortuser = new ArrayList<>(users);
        sortuser.sort((a, b) -> Integer.compare(b.getPoint(), a.getPoint()));
        return sortuser.subList(0, Math.min(count, sortuser.size()));
    }


}
