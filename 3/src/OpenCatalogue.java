import java.util.ArrayList;
import java.util.List;

public class OpenCatalogue {
    List<File> fileList = new ArrayList<>();

    //打开文件数，不能超过16
    int num;
    public boolean openFile(File file)
    {
        if(num<16)
        {
            fileList.add(file);
            num++;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean closeFile(File file)
    {
        if(fileList.remove(file))
        {
            num--;
            return true;
        }
        else
        {
            return false;
        }
    }
}
