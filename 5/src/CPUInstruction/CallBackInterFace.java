package CPUInstruction;

import java.util.List;

//用函数回调接口代替函数指针
public interface CallBackInterFace<T> {
    T process(List<Object> param);
}
