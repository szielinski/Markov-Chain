import java.util.Random;

public class PrefixesLinkedList {
   private class Node{
      Node next = null;
      String prefix;
      FollowersLinkedSet followers = new FollowersLinkedSet();
      
      Node(String prefix, String follower){
         this.prefix = prefix;
         followers.add(follower);
      }
   }
   
   private Node head = null;
   private int size = 0;
   
   public boolean add(String prefix, String follower){
      if(head == null){
         //System.out.println("FOUND NULL HEAD");
         head = new Node(prefix, follower);
      }
      
      Node iterator = head;
      Node pIterator = null;
      
      while(iterator != null && !iterator.prefix.equals(prefix)){
         //System.out.println("PREFIX" + prefix);
         pIterator = iterator;
         iterator = iterator.next;
      }
      if(iterator != null){
         //System.out.println("found existing: " + iterator.prefix);
         return iterator.followers.add(follower);
      } else {
         //System.out.println("NULL?");
         pIterator.next = new Node(prefix, follower);
         size++;
         return true;
      }
   }
   
   public String getRandFollower(String prefix){
      Node iterator = head;
      while(iterator != null && !iterator.prefix.equals(prefix)){
         iterator = iterator.next;
      }
      if(iterator == null)
         return null;
      return iterator.followers.getRandom();
   }
   
   public String getRandPrefix(){
      if(size == 0)
         return null;
      Random random = new Random(System.currentTimeMillis());
      int position = random.nextInt(size);
      int counter = 0;
      
      Node current = head;

      while (counter < position) {
         current = current.next;
         counter++;
      }

      return current.prefix;
   }
   
   public void display(){
      Node iterator = head;
      while(iterator != null){
         System.out.printf("%-40s", iterator.prefix);   
         iterator.followers.display();
         System.out.println();
         iterator = iterator.next;
      }
   }
}
