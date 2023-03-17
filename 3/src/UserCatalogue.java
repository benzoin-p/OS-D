import java.util.ArrayList;
import java.util.List;


public class UserCatalogue {
    List<File> fileList = new ArrayList<>();
    //保存文件数，不能超过10
    int num;

    public boolean addFile(File file)
    {
        if(num<10)
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

    public boolean removeFile(File file)
    {
        if(fileList.remove(file))
        {
            num--;
            file.freeBlock(file.getNext());
            return true;
        }
        else
        {
            return false;
        }
    }
}
