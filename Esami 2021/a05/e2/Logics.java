package a05.e2;

import java.util.function.BinaryOperator;

public interface Logics{
	
	boolean isCellBoolean(int x, int y);
	
	Operation getCellAsOperation(int x, int y);
	
	boolean getCellAsBool(int x, int y);
	
	void hit(int x, int y);
	
	boolean isOver();
	
	enum Operation {
		AND("AND", (x,y) -> x && y), OR("OR", (x,y) -> x || y), XOR("XOR", (x,y) -> x ^ y);
		
		private String name;
		private BinaryOperator<Boolean> operator;
		
		private Operation(String name, BinaryOperator<Boolean> operator) {
			this.name = name;
			this.operator = operator;
		}

		public String getName() {
			return name;
		}

		public BinaryOperator<Boolean> getOperator() {
			return operator;
		}
        
	}    
}
