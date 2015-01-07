package code.part1kl.keyworld.world;

import code.part1kl.keyworld.material.Material;
import code.part1kl.keyworld.voxel.Voxel;
import code.part1kl.keyworld.voxel.VoxelType;

/**The World object holds all of the sectors, voxels, materials and rules for running a world.
 * 
 * @author part1kl
 *
 */
public class World {
	/**The size of default Worlds in {@link code.part1kl.keyworld.world.Sector Sectors}*/
	public final int SIZE=10;
	
	public Sector[] sectors;
	
	public World() {
		sectors = new Sector[SIZE*SIZE*SIZE];
		
		
	}
	
	
	
	
	public VoxelType SPACE, STONE;
	private VoxelType[] voxels = new VoxelType[2];
	/**
	 * Initializes {@link code.part1kl.keyworld.voxel.Voxel Voxels} to be used in the game.
	 */
	public void initializeVoxels() {
		SPACE = new VoxelType(0, "space");
		STONE = new VoxelType(1, "stone");
		

		voxels[0] = SPACE;
		voxels[1] = STONE;
	}
	
	public Material GRANITE, SAND;
	public Material[] materials = new Material[2];
	/**
	 * Initializes {@link code.part1kl.keyworld.material.Material Materials} to be used in the game.
	 */
	public void initializeMaterials() {
		
	}
}
