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

        if (size1-size2==1) {
            for (int i=0;i<size1;i++) {
                String temp = w1.substring(0,i)+w1.substring(i+1);
                if (temp.equals(w2))
                    return true;
            }
            return false;
        }
        else if(size1-size2==-1) {
            for (int i=0;i<size2;i++) {
                String temp = w2.substring(0,i)+w2.substring(i+1);
                if (temp.equals(w1))
                    return true;
            }
            return false;
        }
        return false;
    }

    public static boolean IsExist(Set<String> dic, String word){
        if (dic.contains(word))
            return true;
        return false;
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

    public static Stack<String> LadderGenerate(String w1, String w2, Set<String> dic){
        Stack<String> result = new Stack<String>();
        if (IsAdjacent(w1, w2)){
            result.push(w1);
            result.push(w2);
        }
        else{
            Queue<Stack<String>> BFS = new LinkedList<Stack<String>>();
            Stack<String> temp = new Stack<String>();
            temp.push(w2);
            BFS.offer(temp);
            
            /*Set<String> smalldic = new HashSet<String>();

            for(String word : dic){
                if (word.length()==size)
                    smalldic.add(word);
            }*/

            //PrintSet(smalldic);

            while(!BFS.isEmpty()){
                temp = BFS.poll();
                String top = temp.peek();
                int top_size = top.length();
                String neighbour="";
                for (int i=0;i<=top_size;i++) {
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        for (int j=0;j<3;j++){
                            if (j==0){
                                if (i<top_size)
                                    neighbour = top.substring(0, i) + ch + top.substring(i + 1, top_size);
                                else
                                    break;
                            }
                            else if (j==1)
                                neighbour = top.substring(0,i)+ ch + top.substring(i,top_size);
                            else{
                                if (i==0)
                                    neighbour = top.substring(1,top_size);
                                else if (i==top_size-1)
                                    neighbour = top.substring(0,top_size-1);
                                else if (i==top_size)
                                    break;
                                else
                                    neighbour = top.substring(0,i-1) + top.substring(i,top_size);
                            }
                            if (dic.contains(neighbour)) {
                                Stack<String> copy = (Stack<String>) temp.clone();
                                copy.push(neighbour);

                                if (IsAdjacent(neighbour, w1)) {
                                    copy.push(w1);
                                    result = copy;
                                    return result;
                                }
                                BFS.offer(copy);
                                dic.remove(neighbour);
                            }
                        }
                    }
                }
            }
        }
        return result;
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