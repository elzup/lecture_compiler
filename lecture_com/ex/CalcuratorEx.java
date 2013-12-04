package ex;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;


// プログラム本体
class Calcurator {
    // 変数の名前と値の組を格納する HashMap (値は整数限定)
    private HashMap<String, Integer> symbolMap;
    public Calcurator() {
        symbolMap = new HashMap<String, Integer>();

        System.out.println("式の値を求めます (Ctrl-d で終了)");
        BufferedReader reader = new BufferedReader(
                                   new InputStreamReader(System.in));
        while(true) {
            System.out.print("代入文を入力してください: ");
            String line = null;
            try {
                line = reader.readLine();
            }
            catch(IOException e) {
                break;
            }
            if(line == null) // 入力終了 (Ctrl-d)
                break;
            // 文字列 line を Token に分解しリストに
            LinkedList<Token> inputList = stringToTokenList(line);
            // 代入文を仮定しているので先頭は左辺の変数
            String variableName = inputList.poll().toString();
            // 次の "=" を取り除いておく
            inputList.poll();
            // 右辺 ("=" の次) から逆ポーランド記法に変換
            LinkedList<Token> reversePolishList = reverse(inputList);
            // 逆ポーランド記法に変換した結果を表示
            System.out.print("逆ポーランド記法による表現: ");
            for(Token token : reversePolishList)
                System.out.print(token.toString() + " ");
            System.out.println();
            // 逆ポーランド記法の式の値を計算
            Integer value = calculate(reversePolishList);
            // 変数に代入

            symbolMap.put(variableName, value);

            System.out.println(" " + variableName + " <- " + value);
        }
    }
    // 入力された文字列からトークンのリストを生成
    private LinkedList<Token> stringToTokenList(String string) {
        LinkedList<Token> inputList = new LinkedList<Token>();
        StringTokenizer st = new StringTokenizer(string, " ^;=()+-*/", true);
        while (st.hasMoreTokens()) {
            String tokenString = st.nextToken();
            if(!tokenString.equals(" "))
                inputList.add(TokenFactory.newInstance(tokenString));
        }
        return inputList;
    }
    // 入力されたトークンのリストから逆ポーランド記法のリストを生成
    private LinkedList<Token> reverse(LinkedList<Token> inputList) {
        LinkedList<Token> reversePolishList = new LinkedList<Token>();
        inputList.add(TokenFactory.newInstance(";")); // 終了記号
        Stack<Token> tokenStack = new Stack<Token>();
        tokenStack.push(TokenFactory.newInstance("^")); // 開始記号
        do {
            System.out.println("stack: " + tokenStack);
            Token a = inputList.poll();  // 先頭を取り出す
            if(a.getKind() == Token.OPERAND) {
                // オペランド(定数か変数)だったらすぐに出力
                reversePolishList.add(a);
                System.out.println(" 出力: " + reversePolishList);
            }
            else {
                Token s = tokenStack.peek();
                while(s.compareTo(a) == -1) {
                    System.out.println(" 比較: \"" + s + "\" > \"" + a + "\"");
                    // a よりも優先度の高い演算子がスタックにあったら出力
                    reversePolishList.add(tokenStack.pop());
                    System.out.println("stack: " + tokenStack);
                    System.out.println(" 出力: " + reversePolishList);
                    s = tokenStack.peek();
                }
                if(s.compareTo(a) == 1) {
                    System.out.println(" 比較: \"" + s + "\" < \"" + a + "\"");
                    // a より優先度の低い演算子がスタックの先頭だったら
                    // a をスタックに積む
                    tokenStack.push(a);
                    System.out.println("stack: " + tokenStack);
                }
                else if(s.compareTo(a) == 0) {
                    System.out.println(" 比較: \"" + s + "\" == \"" + a + "\"");
                    tokenStack.pop(); // 取り出して捨てる
                    System.out.println("stack: " + tokenStack);
                }
            }
        } while(!tokenStack.empty());
        return reversePolishList;
    }
    // 逆ポーランド記法のトークンリストを演算
    private Integer calculate(List<Token> reversePolishList) {
        // スタックを用意 (とりあえず中身はInteger)
        // 中身は Integer だがオートボクシングにより int のように使える
        Stack<Integer> stack = new Stack<Integer>();
        for(Token token : reversePolishList) {
            // 現在のスタックを表示
            System.out.print(stack);
            // 2項演算子だったら、前に数値が2つ push されているはずなので、
            // それらを pop して演算し、結果の値を push する
            if(token instanceof BinaryOperator) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                System.out.println(" 演算: " + operand1 + ", " + operand2);
                stack.push(((BinaryOperator)token).calc(operand1, operand2));
            }
            else if(token instanceof IntConstant) { // 数値
                System.out.println(" 値を push: " + token.toString());
                // 文字列から数値に変換して push
                stack.push(((IntConstant)token).intValue());
            }
            else { // 演算子でも数値でもないものは識別子
                System.out.println(" 識別子: " + token.toString());
                // すでに値が入っている変数であれば、その値を stack に push

                System.out.println(symbolMap);
                int val = symbolMap.get(token.toString());

                stack.push(val);
                // ...

            }
        }
        // 最終的に stack には計算された値1つが入っている
        return stack.pop();
    }
}


public class CalcuratorEx {
    public static void main(String[] args) {
	Calcurator calcurator = new Calcurator();
    }
}
