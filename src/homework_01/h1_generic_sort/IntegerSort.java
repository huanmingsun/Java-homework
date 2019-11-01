package homework_01.h1_generic_sort;
public class IntegerSort extends AbstractSort<Integer>{
    @Override
    protected int compare(Integer o1, Integer o2){
        return o1 - o2;
    }
}
