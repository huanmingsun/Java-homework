package homework01.h1_generic_sort;
import java.util.Arrays;
public abstract class AbstractSort<T>{
    public void sort(T[] objs){
        //基于compare方法定义排序算法
        Arrays.sort(objs, AbstractSort.this::compare);
    }
    protected abstract int compare(T o1, T o2);
}
