package code.part1kl.keyworld.world;

import code.part1kl.keyworld.material.Material;
import code.part1kl.keyworld.utils.Float3;

/**The World object holds all of the sectors, voxels, materials and rules for running a world.
 * 
 * @author part1kl
 *
 */
public class World {
	/**The size of default Worlds in {@link code.part1kl.keyworld.world.Sector Sectors}*/
	public final int SIZE=1;
	
	public Sector[] sectors;
	private Generate generator;
	
	public World() {
		sectors = new Sector[SIZE*SIZE*SIZE];
		generator = new Generate();
		System.out.println("World created,");
	}
	
	public void loadWorld() {
		sectors[0] = generator.generate(0, 0, 0);
		System.out.println("World Loaded");
	}
	
	public static Material STONE, SAND;
	public static Material[] materials = new Material[2];
	/**
	 * Initializes {@link code.part1kl.keyworld.material.Material Materials} to be used in the game.
	 */
	public void initializeMaterials() {
		STONE = new Material((byte) 0, 5.2734f, "stone", false, false, false, new Float3(0.3f, 0.3f, 0.3f));
		SAND = new Material((byte) 1, 5.0781f, "sand", false, false, false, new Float3(.6f, .6f, .4f));
		
		materials[0]=STONE;
		materials[1]=SAND;
	}
}
