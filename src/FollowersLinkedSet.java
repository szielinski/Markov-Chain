import java.util.Random;

public class FollowersLinkedSet {
   
   private static class Node {
      private String follower;
      private Node next;

      Node(String follower, Node next) {
         this.follower = follower;
         this.next = next;
      }
   }

   private Node head = null;
   private int size = 0;   //used for randomization

   public boolean add(String follower) {
      if (contains(follower))
         return false;
      head = new Node(follower, head);
      //System.out.println("added follower " +follower);
      size++;
      //System.out.println(size);
      return true;
   }

   public boolean contains(String follower) {
      Node current = head;

      while (current != null && !current.follower.equals(follower)) {
         current = current.next;
      }

      return current != null;
   }

   public String getRandom() {
      Random random = new Random(System.currentTimeMillis());
      int randPos = random.nextInt(size);
      Node current = head;

      int counter = 0;
      while (counter < randPos) {
         current = current.next;
         counter++;
      }

      return current.follower;
   }
   
   public int getSize(){
      return size;
   }
   
   public void display() {
      Node current = head;

      while (current != null) {
         System.out.printf("\"%s\"", current.follower);
         if(current.next != null)
            System.out.printf(", ");
            
         current = current.next;
      }
   }
}
