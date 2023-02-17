import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.io.IOException;
import java.io.*;
import javax.activation.*;

public class EmailSender {

    public static void main(String[] args) throws IOException, MessagingException {
        Userdata vCode = new Userdata();


        final Properties properties = new Properties();
        properties.load(EmailSender.class.getClassLoader().getResourceAsStream("mail.properties"));


        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress("razercsgo@inbox.ru"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("doshchanov_alikhan@mail.ru"));
        message.setSubject("Java Password Reset");
        message.setText(EmailSender.getRecoveryCode());

        Transport tr = mailSession.getTransport();
        tr.connect(null, "Dvd4wDUyjLxMYZgawB71");
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
    }
    public static String getRecoveryCode(){
        String code = "";
        try(FileReader reader = new FileReader("passwordToken.txt"))
        {
            int c;
            while((c=reader.read())!=-1){

                code += (char)c;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return code;
    }

}
