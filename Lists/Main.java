
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        ThreeTenDynamicArrayList<String> list1 = new ThreeTenDynamicArrayList<>(4);
        // 1) add some values
        list1.add("1000SXXX");
        list1.add("2000SXXX");
        list1.add("3000SXXX");
        list1.add("4000SXXX");
        
        list1.toString();

        list1.remove(1);
        list1.toString();

        list1.add(0,"888SXXX");
        list1.toString();
        System.out.println(list1.capacity());
        list1.setCapacity(33);
        list1.toString();
        list1.setCapacity(5);
        list1.toString();
        System.out.println(list1.setCapacity(2));
        list1.set(2,"99999SXXX");
        list1.add(4,"9898989SXXX" );
        list1.add(5,"345532334SXXX");
        list1.add(1,"99665543SXXX");
        list1.add("3234");
        list1.remove(2);
        list1.remove(String.valueOf("4000"));
        System.out.println(list1.remove(String.valueOf("40020")));
        list1.toString();


        Iterator<String> iter = list1.iterator();
        System.out.println(iter.hasNext());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.hasNext());        
        


    }
}
