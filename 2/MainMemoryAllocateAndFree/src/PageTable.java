import java.util.ArrayList;

public class PageTable
{
    public ArrayList<Integer> blockNum = new ArrayList<>();

    public void printPageTable()
    {
        System.out.println("┌───────┬───────┐");
        System.out.println("│页号\t"+"│块号\t│");
        System.out.println("├───────┼───────┤");
        if(!blockNum.isEmpty())
        {
            for(int i=0;i<blockNum.size();i++)
            {
                System.out.println("│"+i+"\t\t"+"│"+blockNum.get(i)+"\t\t│");
            }
        }
        System.out.println("└───────┴───────┘");
    }
}
