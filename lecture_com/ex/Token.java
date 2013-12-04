package ex;

// トークンを表す抽象クラス
// トークン間の比較のために Comparable インタフェースを実装
public class Token implements Comparable<Token> {
    // トークンの文字列表現
    private String string;
    // トークンの種類
    protected int kind;
    // トークンの種類を表す定数
    // 値は演算子順位行列の添字と対応していれば何でもよく、順不同
    public static final int START = 0;  // 始
    public static final int SUM = 1;    // +
    public static final int SUB = 2;    // -
    public static final int MUL = 3;    // *
    public static final int DIV = 4;    // /
    public static final int LPAREN = 5; // (
    public static final int RPAREN = 6; // )
    public static final int OPERAND = 7; // 識別子(変数)または定数
    public static final int END = 8;    // 終
    // 演算子順位行列
    // トークンを追加する場合には、対応する行と列の両方を追加
    private static final int order[][] = { // [左][右]
	// 始  +  -  *  /  (  ) 識別子 終 <-右  左
	{   9, 1, 1, 1, 1, 1, 9, 1,     0 }, // 始
	{   9,-1,-1, 1, 1, 1,-1, 1,    -1 }, // +
	{   9,-1,-1, 1, 1, 1,-1, 1,    -1 }, // -
	{   9,-1,-1,-1,-1, 1,-1, 1,    -1 }, // *
	{   9,-1,-1,-1,-1, 1,-1, 1,    -1 }, // /
	{   9, 1, 1, 1, 1, 1, 0, 1,     9 }, // (
	{   9, 9, 9, 9, 9, 9, 9, 9,     9 }, // )
	{   9,-1,-1,-1,-1, 9,-1, 9,    -1 }, // 識別子
	{   9, 9, 9, 9, 9, 9, 9, 9,     9 }, // 終
    };
    // コンストラクタ
    public Token(String string) {
	this.string = string;
    }
    public String toString() {
	return string;
    }
    public int getKind() {
	return kind;
    }
    // トークン間の比較
    public int compareTo(Token anotherToken) {
	return order[this.kind][anotherToken.getKind()];
    }
}


