package daily.cn.studydemo.algorithm;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 * http://blog.csdn.net/morewindows/article/details/6684558
 * </pre>
 */

public class QuickSort {

    public static void main(String[] args) {
        int [] arrays={72,6,57,88,60,42,83,73,48,85};
        for (int i=0;i<arrays.length;i++){
            System.out.println(arrays[i]);
        }
        System.out.println("------------------");
        quickSort(arrays,0,arrays.length-1);
        for(int i=0;i<arrays.length;i++){
            System.out.println(arrays[i]);
        }
    }
    /***
     *
     * 对挖坑填数进行总结
     1．i =L; j = R; 将基准数挖出形成第一个坑a[i]。
     2．j--由后向前找比它小的数，找到后挖出此数填前一个坑a[i]中。
     3．i++由前向后找比它大的数，找到后也挖出此数填到前一个坑a[j]中。
     4．再重复执行2，3二步，直到i==j，将基准数填入a[i]中。
     照着这个总结很容易实现挖坑填数的代码：
     */
    public static void quickSort(int[] arrays, int left, int right) {
        if(arrays==null||arrays.length<=0)
            return;
        if (left < right) {
            int partion = partion(arrays, left, right);
            quickSort(arrays, left, partion - 1);
            quickSort(arrays, partion + 1, right);
        }
    }

    /**
     * 返回调整后基准数的位置
     */
    private static int partion(int[] arrays, int left, int right) {
        int i = left, j = right, X = arrays[left];//s[l]即s[i]就是第一个坑
        while (i < j) {
            while (i < j && arrays[j] >= X) // 从右向左找小于x的数来填s[i]
                j--;
            if (i < j) {
                arrays[i++] = arrays[j];//将s[j]填到s[i]中，s[j]就形成了一个新的坑
            }
            while (i < j && arrays[i] < X) // 从左向右找大于或等于x的数来填s[j]
                i++;
            if (i < j) {
                arrays[j--] = arrays[i]; //将s[i]填到s[j]中，s[i]就形成了一个新的坑
            }
        }
        arrays[i] = X;//退出时，i等于j。将x填到这个坑中。
        return i;
    }
}
