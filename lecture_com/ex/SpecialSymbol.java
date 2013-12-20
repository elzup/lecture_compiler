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

// 単項演算子の抽象クラス。1引数の calc メソッドを持つ
abstract class UnarioOperator extends Token {
	public UnarioOperator(String string) {
		super(string);
	}
	public abstract double calc(double operand1);
}

// 2項演算子の抽象クラス。2引数の calc メソッドを持つ
abstract class BinaryOperator extends Token {
	public BinaryOperator(String string) {
		super(string);
	}
	public abstract double calc(double operand1, double operand2);
}

// 2項演算子 +
class Sum extends BinaryOperator {
	public Sum(String string) {
		super(string);
		kind = SUM;
	}

	public double calc(double operand1, double operand2) {
		return operand1 + operand2;
	}
}

// 2項演算子 -
class Sub extends BinaryOperator {
	public Sub(String string) {
		super(string);
		kind = SUB;
	}

	public double calc(double operand1, double operand2) {
		return operand1 - operand2;
	}
}

// 2項演算子 *
class Mul extends BinaryOperator {
	public Mul(String string) {
		super(string);
		kind = MUL;
	}

	public double calc(double operand1, double operand2) {
		return operand1 * operand2;
	}
}

// 2項演算子 /
class Div extends BinaryOperator {
	public Div(String string) {
		super(string);
		kind = DIV;
	}

	public double calc(double operand1, double operand2) {
		return operand1 / operand2;
	}
}

//------------------- add ex -------------------//
// 2項演算子 <
class Rem extends BinaryOperator {
	public Rem(String string) {
		super(string);
		kind = REM;
	}
	public double calc(double operand1, double operand2) {
		return operand2 % operand1;
	}
}

// 2項演算子 %
class Pow extends BinaryOperator {
	public Pow(String string) {
		super(string);
		kind = POW;
	}
	public double calc(double operand1, double operand2) {
		return (double)Math.pow(operand1, operand2);
	}
}

// 2項演算子 .
class Decima extends BinaryOperator {
	public Decima(String string) {
		super(string);
		kind = DECIM;
	}
	public double calc(double operand1, double operand2) {
		return new Double(String.format("%d.%d", (int)operand2, (int)operand1));
	}
}

// 2項演算子 ?
class Max extends BinaryOperator {
	public Max(String string) {
		super(string);
		kind = DECIM;
	}
	public double calc(double operand1, double operand2) {
		return Math.max(operand1, operand2);
	}
}


// 単項演算子 !
class Fact extends UnarioOperator {
	public Fact(String string) {
		super(string);
		kind = FACT;
	}
	@Override
	public double calc(double operand1) {
		assert 0 < operand1 && operand1< 100;
		double k = 1;
		for (double i = 2; i <= operand1; i++)
			k *= i;
		return k;
	}
}

//------------------- add ex end -------------------//

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

//// 浮動小数点型
//class DoubleConstant extends Token {
//	private double value;

//	public DoubleConstant(String string) {
//		super(string);
//		try {
//			value = Double.parseDouble(string);
//		} catch (Exception e) {
//			System.err.println(e);
//		}
//		kind = OPERAND;
//	}

//	public double doubleValue() {
//		return value;
//	}
//}


// 識別子(変数名)
class Identifier extends Token {
	public Identifier(String string) {
		super(string);
		kind = OPERAND;
	}
}

