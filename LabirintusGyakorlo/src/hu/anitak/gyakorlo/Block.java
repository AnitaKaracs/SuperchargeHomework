package hu.anitak.gyakorlo;

public enum Block {
	CORRIDOR(" "), WALL("1"), COIN("O");
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	Block(String value) {
		this.value = value;
	}
}
