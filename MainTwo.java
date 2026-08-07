
import java.util.Iterator;

public class MainTwo {
    public static void main(String[] args) {
     
        ThreeTenLinkedList <Integer> list2 = new ThreeTenLinkedList<>();
        System.out.println(list2.getHead());
        list2.add(111);
        list2.add(222);
        list2.add(333);
        System.out.println(list2.getHead());
        
        list2.add(3,444);
        list2.add(1,444);
        list2.add(0,000);

       list2.remove(Integer.valueOf(333));
       list2.set(2, 888);


        System.out.println(list2.size());


        System.out.println(list2.toString());

        


        Iterator<Integer> it = list2.iterator();
        while (it.hasNext()) {
            Integer val = it.next();
            System.out.println(val);
        }





    }
}
