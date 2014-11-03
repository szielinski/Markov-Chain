import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MarkovChain {
   private static final String CORRECT_USAGE = "Incorrect usage. Start the program from the command line using: \'java MarkovChain [inputFileName].txt [prefix size] [output word count]\'";

   private HashTable hashTable = null;
   
   public MarkovChain(String filename, int prefix, int outputSize){
      processFile(filename, prefix);
      processOutput(outputSize);
   }
   
   private void processFile(String inFilename, int prefix) {
      Scanner in = null;

      int wordCount = 0;
      try {
         in = new Scanner(new FileReader(inFilename));
         while (in.hasNext()) {
            in.next();
            wordCount++;
         }
      } catch (FileNotFoundException e) {
         System.err.printf("File: %s not found.\n", inFilename);
         e.printStackTrace();
      } finally {
         in.close();
      }

      hashTable = new HashTable(wordCount - prefix - 1);

      try {
         in = new Scanner(new FileReader(inFilename));
         String[] selection = new String[prefix];
         int index = 0;

         // fill in the array initially
         while (in.hasNext()) {
            String word = in.next();
            selection[index] = word;
            index++;
            if (index == prefix) {
               index %= prefix;
               break;
            }
         }

         while (in.hasNext()) {
            // read in the next word
            String word = in.next();

            // create a string suitable for the prefix column
            // add index up to end + start up to index -1 here
            StringBuilder a = new StringBuilder();

            int count = 0;
            for (int i = index; i < prefix; i++) {
               a.append(selection[i]);
               if (++count < prefix)
                  a.append(" ");
            }
            for (int i = 0; i < index; i++) {
               a.append(selection[i]);
               if (++count < prefix)
                  a.append(" ");
            }
            // add both to the hash table
            //System.out.printf("\nadding %s %s\n", a.toString(), word);
            //System.out.println(hashTable.add(a.toString(), word));
            hashTable.add(a.toString(), word);

            selection[index] = word;
            index = (index + 1) % prefix;
         }
      } catch (FileNotFoundException e) {
         System.err.printf("File: %s not found.\n", inFilename);
         e.printStackTrace();
      } finally {
         in.close();
      }

//      hashTable.display();
   }
   
   private void processOutput(int outputSize){
      String prefix = hashTable.getRandomPrefix();
      String follower = hashTable.getRandomFollower(prefix);
      
      System.out.print(prefix);
      
      String[] window = prefix.split(" ");
      int count = window.length + 1;
      while(count < outputSize || prefix.charAt(prefix.length()-1) != '.'){
         System.out.print(" " + follower);
         String[] temp = prefix.split(" ", 2);
         prefix = temp[1] + " " + follower;
         follower = hashTable.getRandomFollower(prefix);
         count++;
      }
   }

   public static void main(String[] args) {
      long start = System.currentTimeMillis();
      new MarkovChain("big.txt", 2, 100);
      long end = System.currentTimeMillis() - start;
      System.out.println("\n"+end);
//      if(args.length < 3)
//         System.out.println(CORRECT_USAGE);
//      new MarkovChain(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
   }
}
