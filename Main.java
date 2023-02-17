import javax.mail.MessagingException;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.sql.Connection;
public class Main {

    public static void main(String[] args) throws MessagingException, IOException, NoSuchAlgorithmException {
        Scanner userdata = new Scanner(System.in);
        Userdata data = new Userdata();
        LogPassCheck correctData = new LogPassCheck();
        Database db = new Database();
        Encryptor encryptor = new Encryptor();


        Connection conn = db.connectToDB("database", "postgres", "soil467seamwall");

        String realLog = db.readEmail(conn, "users");
        String hashedRealPassword = db.readPassword(conn, "users");


        int attempts = 3;

        while (attempts != 0) {

            System.out.print("Enter your login: ");
            data.setInputString(userdata.nextLine()); //set login to whatever we input

            System.out.print("Enter your password: ");
            data.setInputPassword(userdata.nextLine()); //set password to whatever we input

            String inputLog = data.getInputString();
            String inputPassword = data.getInputPassword();

            if (correctData.checkData(inputLog, inputPassword)) {
                System.out.println("The minimum length of the login and password is 6 characters");
                System.out.println();
                continue;
            }
            if (!correctData.checkString(inputPassword)) {
                System.out.println("Password should contain at least 1 capital, 1 lower case letter and a number");
                System.out.println();
                continue;
            }

            if (inputLog.equals(realLog) && encryptor.encryptString(inputPassword).equals(hashedRealPassword)) {
                System.out.print("You have entered!");
                return;
            } else {
                attempts = attempts - 1;
                System.out.println("Login or password is incorrect");

                if (attempts != 1) {
                    System.out.println(attempts + " attempts left");
                    System.out.println();
                } else {
                    System.out.println(attempts + " attempt left");
                    System.out.println();
                }
            }

        }
        if(attempts == 0){
            System.out.println("Reset password? Y or N");

            Scanner reset = new Scanner(System.in);
            String answer = reset.nextLine();

            if (answer.equals("Y")){
                data.codeWriter();
                EmailSender.main(null);
                System.out.println("We sent code on your email, please enter it: ");
                String code = reset.nextLine();



                if (code.equals(EmailSender.getRecoveryCode())){
                    while (true){
                        System.out.println("Enter new password");
                        data.setUserResetPassword(reset.nextLine());

                        String userResetPas = data.getUserResetPassword();

                        if (correctData.checkData(data.getInputString(), userResetPas)) {
                            System.out.println("The minimum length of the login and password is 6 characters");
                            System.out.println();
                            continue;
                        }
                        if (!correctData.checkString(userResetPas)) {
                            System.out.println("Password should contain at least 1 capital, 1 lower case letter and a number");
                            System.out.println();
                            continue;
                        }
                        data.setRealPassword(encryptor.encryptString(userResetPas));
                        db.updateData(conn, "users", data.getRealPassword());
                        System.out.println("Password has been successfully changed!");
                        break;
                    }


                }
                else {
                    System.out.println("Verification code fail. Stop trying to hack this user!");
                }
            }
            else if (answer.equals("N")){
                System.out.println("End");
            }


        }
    }
}
