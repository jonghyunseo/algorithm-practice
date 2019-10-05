import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;




public class Solution {
    public static abstract class Heap
    {
        int[] heapArray;
        int pos = 0;

        Heap()
        {
            heapArray = new int[128];
        }

        abstract boolean compare(int a, int b);

        public void display()
        {
            int pow = 1;
            for(int i = 1; i <= pos; i++)
            {
                System.out.print(heapArray[i]+ "   ");
            }
            System.out.println();
        }

        public int getTop( )
        {
            return heapArray[1];
        }

        public int extractTop()
        {
            return removeTop();
        }

        public int removeTop()
        {
            int top = heapArray[1];

            if( pos > 0){
                heapArray[1] = heapArray[pos];
                pos--;
                heapify(getParent(pos));
            }
            return top;
        }

        public int getCount()
        {
            return pos;   
        }

        public void insert(int data)
        {
            heapArray[++pos] = data;
            heapify(pos);
        }

        private void heapify(int position)
        {
            if( position < 1 )
                return;

            int parent = getParent(position);

            if( parent < 1){
                return;
            }

            if(compare( heapArray[position], heapArray[parent]))
            {
                swap(position, parent);
                heapify(parent);
            }
        }

        private void swap(int a, int b)
        {
            int temp = heapArray[a];
            heapArray[a] = heapArray[b];
            heapArray[b] = temp;
        }

        private int getParent(int position)
        {
            return (position -1 ) / 2;
        }
    }


    public static class MaxHeap extends Heap{

        @Override
        public boolean compare(int a, int b)
        {
            if( a > b )
                return true;
            return false;
        }
    }

    public static class MinHeap extends Heap{
        @Override
        public boolean compare(int a, int b)
        {
            if( a < b )
                return true;
            return false;
        }
    }


    /*
     * Complete the runningMedian function below.
     */

    public static double average(int a, int b)
    {
        return ((double)a+b)/2;
    }

    public static int checkBalance()
    {
        if( l.getCount() > r.getCount())
        {
            return 1;
        }else if( l.getCount() == r.getCount())
        {
            return 0;
        }else 
        {
            return -1;
        }
    }


    static MaxHeap l = new MaxHeap();
    static MinHeap r = new MinHeap();
    static double[] runningMedian(int[] a) {
        /*
         * Write your code here.
         */
        double medians[] = new double[a.length];
        double median = 0;
        for(int i = 0 ; i < a.length; i++)
        {
            
            System.out.print("LEFT ");
            //l.insert(a[i]);
            l.display();

            System.out.print("RIGHT ");
            //r.insert(a[i]);
            r.display();

            int value = a[i];
            int b = checkBalance();      
            switch(b){
                case 1:
                    if( median < value)
                    {
                        r.insert(value);
                    }else {
                        r.insert(l.extractTop());
                        l.insert(value);
                    }
                    median = average(l.getTop(), r.getTop());
                break;
                case 0:
                    if( median < value)
                    {
                        r.insert(value);
                    }else {
                        l.insert(value);
                    }
                    median = value;
                break;
                case -1:
                    if( median < value)
                    {
                        l.insert(r.extractTop());
                        r.insert(value);
                    }else {
                        l.insert(value);
                    }
                    median = average(l.getTop(), r.getTop());
                break;
            }
            medians[i] = median;
        }
        return medians;
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = Integer.parseInt(scanner.nextLine().trim());

        int[] a = new int[aCount];

        for (int aItr = 0; aItr < aCount; aItr++) {
            int aItem = Integer.parseInt(scanner.nextLine().trim());
            a[aItr] = aItem;
        }

        double[] result = runningMedian(a);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            bufferedWriter.write(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}
