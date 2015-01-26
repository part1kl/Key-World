package code.part1kl.keyworld.world;

import code.part1kl.keyworld.material.Material;
import code.part1kl.keyworld.utils.Float3;
import code.part1kl.keyworld.utils.Float4;

/**The World object holds all of the sectors, voxels, materials and rules for running a world.
 * 
 * @author part1kl
 *
 */
public class World {
	/**The size of default Worlds in {@link code.part1kl.keyworld.world.Sector Sectors}*/
	public static final int SIZE=12, WORLD_SECTOR_HEIGHT=4, WORLD_HEIGHT=50;
	
	public Sector[][][] sectors;
	private Generate generator;
	
	public World() {
		sectors = new Sector[SIZE][WORLD_SECTOR_HEIGHT][SIZE];
		generator = new Generate((int)Math.random());
		System.out.println("World created,");
	}
	
	public void loadWorld() {
		
		for(int xi=0; xi<World.SIZE; xi++) {
        	for(int yi=0; yi<World.WORLD_SECTOR_HEIGHT; yi++) {
        		for(int zi=0; zi<World.SIZE; zi++) {
        			sectors[xi][yi][zi] = generator.generate(xi, yi, zi);
        		}
        	}
		}
		System.out.println("World Loaded");
	}
	
	public Sector getSector(int x, int y, int z) {
		return sectors[x][y][z];
	}
	
	public static Material AIR, STONE, SAND;
	public static Material[] materials = new Material[3];
	/**
	 * Initializes {@link code.part1kl.keyworld.material.Material Materials} to be used in the game.
	 */
	//1.225/100mg per cm^3
	public void initializeMaterials() {
		AIR = new Material((byte) 0, 0f, "air", false, false, false, new Float4(1f, 1f, 1f, 0.001f));
		STONE = new Material((byte) 1, 5.2734f, "stone", false, false, false, new Float4(0.3f, 0.3f, 0.3f, 1.0f));
		SAND = new Material((byte) 2, 5.0781f, "sand", false, false, false, new Float4(.6f, .6f, .4f, 1.0f));
		
		materials[0]=AIR;
		materials[1]=STONE;
		materials[2]=SAND;
	}
}
