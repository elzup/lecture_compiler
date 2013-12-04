package ex;

// Token のサブクラスのインスタンスを生成するための Factory クラス
public class TokenFactory {
    public static Token newInstance(String string) {
	Token instance;
	if(string.equals("^") || string.equals(";") || string.equals("="))
	    instance = new SpecialSymbol(string);
	else if(string.equals("+"))
	    instance = new Sum(string);
	else if(string.equals("-"))
	    instance = new Sub(string);
	else if(string.equals("*"))
	    instance = new Mul(string);
	else if(string.equals("/"))
	    instance = new Div(string);
	else if(string.equals("("))
	    instance = new LParen(string);
	else if(string.equals(")"))
	    instance = new RParen(string);
	else if(string.matches("\\d+"))  // 数字の並びは整数定数
	    instance = new IntConstant(string);
	else // 識別子(変数)
	    instance = new Identifier(string);
	return instance;
    }
}

