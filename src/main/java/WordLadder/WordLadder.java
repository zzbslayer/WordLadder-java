package WordLadder;

import java.io.*;
import java.util.*;

public class WordLadder{
    public static boolean IsAdjacent(String w1,String w2){
        int difference = 0;

        int size1 = w1.length();
        int size2 = w2.length();

        char[] c1 = w1.toCharArray();
        char[] c2 = w2.toCharArray();

        if (size1==size2){
            for (int pos=0;pos<size1;pos++){
                //System.out.print(c1[pos]);
                //System.out.println(c2[pos]);
                if (c1[pos] != c2[pos])
                    difference++;
                if (difference>1)
                    return false;
            }
            if (difference==1)
                return true;
        }
        return false;
    }

    public static void PrintStack(Stack<String> s){
        String result="";
        for (String w : s)
            result = w + " " + result;
        System.out.println(result);
    }

    public static void PrintLadder(Stack<String> s, String w1, String w2){
        int size = s.size();
        if (size!=0){
            System.out.println("A ladder from " + w2 + " back to " + w1 + ":");
            PrintStack(s);
        }
        else
            System.out.println("No word ladder from " + w2 + " back to " + w1 + ".");
    }

    public static void PrintSet(Set<String> s){
        for (String w : s){
            System.out.println(w);
        }
    }

    public static Set<String> DicGenerate(String filename)throws IOException{
        Set<String> result = new HashSet<String>();
        
        DataInputStream in = new DataInputStream(new FileInputStream(filename));
        BufferedReader br  = new BufferedReader(new InputStreamReader(in));
        String temp;

        while((temp = br.readLine())!=null)
            result.add(temp);
        br.close();
        return result;
    }

    public static boolean IsExist(Set<String> dic, String word){
        if (dic.contains(word))
            return true;
        return false;
    }

    public static Stack<String> LadderGenerate(String w1, String w2, Set<String> dic){
        Stack<String> result = new Stack<String>();
        if (IsAdjacent(w1, w2)){
            result.push(w1);
            result.push(w2);
        }
        else{
            boolean found = false;
            Queue<Stack<String>> BFS = new LinkedList<Stack<String>>();
            Stack<String> temp = new Stack<String>();
            temp.push(w2);
            BFS.offer(temp);

            int size = w2.length();
            
            Set<String> smalldic = new HashSet<String>();

            for(String word : dic){
                if (word.length()==size)
                    smalldic.add(word);
            }

            //PrintSet(smalldic);

            while(!BFS.isEmpty()){
                temp = BFS.poll();
                String top = temp.peek();
                //System.out.println(top);
                int top_size = top.length();
                smalldic.remove(w2);
                for (int i=0;i<top_size;i++){
                    String neighbour;
                    for (char ch='a';ch<='z';ch++){
                        neighbour = top.substring(0,i)+ch+top.substring(i+1,top_size);
                        //System.out.println("neighbour : "+neighbour);
                        if(smalldic.contains(neighbour)){
                            //System.out.println('\t'+neighbour);
                            Stack<String> copy = (Stack<String>)temp.clone();
                            copy.push(neighbour);
                            //PrintStack(temp);
                            //PrintStack(copy);
                            if (IsAdjacent(neighbour, w1)){
                                copy.push(w1);
                                result = copy;
                                found = true;
                                break;
                            }
                            BFS.offer(copy);
                            smalldic.remove(neighbour);
                        }
                    }
                    if (found)
                        break;
                }
                if (found)
                    break;
            }
        }
        return result;
    }

    public static boolean IsValid(String w){
        if (w=="")
            return false;
        char[] ca = w.toCharArray();
        for (char ch : ca){
            if (!Character.isLetter(ch))
                return false;
        }
        return true;
    }

    public static void main(String args[])throws IOException{
        /* if (isAdjacent(args[0],args[1]))
            System.out.println("bingo");
        else
            System.out.println("not"); */

        if (args.length!=3){
            System.out.println("Less or more parameters");
        }
        Set<String> dic = DicGenerate(args[0]);
        String w1 = args[1];
        String w2 = args[2];
        if (!IsValid(w1)){
            System.out.println("The first word " + w1 + " is invalid.");
            return;
        }
        if (!IsValid(w2)){
            System.out.println("The first word " + w2 + " is invalid.");
            return;
        }

        w1 = w1.toLowerCase();
        w2 = w2.toLowerCase();

        if (w2.length()!=w1.length()){
            System.out.println("The two words must be the same length.");
            return;
        }
        if (!IsExist(dic,w1)||!IsExist(dic,w2)){
			System.out.println("The two words must be found in the dictionary.");
			return;
        }
        if (w1==w2){
			System.out.println("The two words must be different.");
			return;
        }
        Stack<String> result = LadderGenerate(w1, w2, dic);
        PrintLadder(result,w1,w2);

        /*
        Set<String> dic = DicGenerate(args[0]);
        if (IsExist(dic,"bug"))
            System.out.println("bingo");
        if (!IsExist(dic,"boo"))
            System.out.println("bingo * 2");
            */
        
    }
}