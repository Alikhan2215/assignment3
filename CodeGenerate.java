import  java.util.Random;
public class CodeGenerate {

    public String generateCode(){
        Random random = new Random();
        int x = random.nextInt(899999)+100000;
        String code = Integer.toString(x);
        return code;
    }

}
