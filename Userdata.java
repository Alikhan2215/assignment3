import java.io.*;
import java.util.Scanner;

public class Userdata {

    private String realPassword;
    private String inputString;
    private String inputPassword;
    private String userResetPassword;

    public String getUserResetPassword() {
        return userResetPassword;
    }

    public void setUserResetPassword(String userResetPassword) {
        this.userResetPassword = userResetPassword;
    }

    public String getRealPassword(){
        return realPassword;
    }
    public void setRealPassword(String newRealPassword){
        this.realPassword = newRealPassword;
    }


    public String getInputString(){ //Getter
      return inputString;
    }
    public void setInputString(String newLogin){ //Setter
      this.inputString = newLogin;
    }


    public String getInputPassword(){
      return inputPassword;
    }
    public void setInputPassword(String newPassword){
        this.inputPassword = newPassword;
    }

    public String getVerCode(){
        CodeGenerate vCode = new CodeGenerate();
        return vCode.generateCode();
    }

    public void codeWriter(){

        try(FileWriter writer = new FileWriter("passwordToken.txt", false))
        {
            writer.write(this.getVerCode());
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }



}


