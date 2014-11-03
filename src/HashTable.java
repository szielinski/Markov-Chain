import java.util.Random;

public class HashTable {
   private PrefixesLinkedList[] hashTable;   
   private static final double TABLE_SCALING_FACTOR = 1.5;
   
   public HashTable(int inputSize){
      int size = (int) (TABLE_SCALING_FACTOR * inputSize);
      hashTable = new PrefixesLinkedList[size];
      
      for(int i=0; i<size; i++){
         hashTable[i] = new PrefixesLinkedList();
      }
   }
   
   private int hash(String prefix){
      return Math.abs(prefix.hashCode() % hashTable.length);
   }
   
   public boolean add(String prefix, String follower){
      //System.out.println("HASHING " + prefix);
      int index = hash(prefix);
      //System.out.println("index " + index);
      return hashTable[index].add(prefix,follower);
   }
   
   public String getRandomPrefix(){
      Random random = new Random(System.currentTimeMillis());
      int position = random.nextInt(hashTable.length);
      
      String prefix = hashTable[position].getRandPrefix();
      while(prefix == null){
         position = random.nextInt(hashTable.length);
         prefix = hashTable[position].getRandPrefix();
      }
      return prefix;
   }
   
   public String getRandomFollower(String prefix){
      return hashTable[hash(prefix)].getRandFollower(prefix);
   }
   
   public void display(){
      System.out.printf("\n%-40s%s\n", "Prefix", "Followers");
      System.out.println("--------------------------------------------------------------------------------");
      for(int i=0; i<hashTable.length; i++){
         hashTable[i].display();
      }
   }
}
