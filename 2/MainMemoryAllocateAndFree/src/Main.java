import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("[Information] 模拟开始!");
        BitMap bitMap = new BitMap();
        Work work1 = new Work();
        Work work2 = new Work();
        Work work3 = new Work();
        boolean conti = true;
        Scanner s = new Scanner(System.in);
        while(conti)
        {
           work1.chooseFunc(bitMap);
           work2.chooseFunc(bitMap);
           work3.chooseFunc(bitMap);
           System.out.print("[Information] 是否继续？(y/n)");
           String str = s.next();
           if(!str.equals("y"))
           {
               conti=false;
               System.out.println("[Information] 模拟结束!");
           }
        }
    }
}
