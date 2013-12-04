package ex;

// 特殊記号 ^ ; =
public class SpecialSymbol extends Token {
	public SpecialSymbol(String string) {
		super(string);
		if (string.equals("^"))
			kind = START;
		else if (string.equals(";"))
			kind = END;
		// "=" は扱わないので何もしない
	}
}

// 2項演算子の抽象クラス。2引数の calc メソッドを持つ
abstract class BinaryOperator extends Token {
	public BinaryOperator(String string) {
		super(string);
	}

	public abstract int calc(int operand1, int operand2);
}

// 2項演算子 +
class Sum extends BinaryOperator {
	public Sum(String string) {
		super(string);
		kind = SUM;
	}

	public int calc(int operand1, int operand2) {
		return operand1 + operand2;
	}
}

// 2項演算子 -
class Sub extends BinaryOperator {
	public Sub(String string) {
		super(string);
		kind = SUB;
	}

	public int calc(int operand1, int operand2) {
		return operand1 - operand2;
	}
}

// 2項演算子 *
class Mul extends BinaryOperator {
	public Mul(String string) {
		super(string);
		kind = MUL;
	}

	public int calc(int operand1, int operand2) {
		return operand1 * operand2;
	}
}

// 2項演算子 /
class Div extends BinaryOperator {
	public Div(String string) {
		super(string);
		kind = DIV;
	}

	public int calc(int operand1, int operand2) {
		return operand1 / operand2;
	}
}

// 左括弧
class LParen extends Token {
	public LParen(String string) {
		super(string);
		kind = LPAREN;
	}
}

// 右括弧
class RParen extends Token {
	public RParen(String string) {
		super(string);
		kind = RPAREN;
	}
}

// 整数定数
class IntConstant extends Token {
	private int value;

	public IntConstant(String string) {
		super(string);
		try {
			value = Integer.parseInt(string);
		} catch (Exception e) {
			System.err.println(e);
		}
		kind = OPERAND;
	}

	public int intValue() {
		return value;
	}
}

// 識別子(変数名)
class Identifier extends Token {
	public Identifier(String string) {
		super(string);
		kind = OPERAND;
	}
}
