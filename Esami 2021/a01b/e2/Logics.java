package a01b.e2;

public interface Logics {
    
	enum HitType {
		FIRST, SECOND, THIRD, OVER
	}
	
	void hit(int x, int y);
	
	boolean isSelected(int x, int y);

    boolean isFirst(int x, int y);

    boolean isSecond(int x, int y);

    boolean isThird(int x, int y);
	
	boolean isOver();
}
