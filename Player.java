
class Player {
	
	private Room currentRoom;
	int health = 50;
	int damage = 10;

	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void damage(int damage) {
		this.setHealth(this.getHealth() -damage);
	}
	public boolean isAlive() {
		if (health > 0) {
			return true;
		}
		return false;
	}
	
}
