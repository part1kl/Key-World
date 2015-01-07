package code.part1kl.keyworld.voxel;

/**
 * 
 * @author part1kl
 *
 */
public class Voxels {

	public Voxel SPACE, STONE;
	
	private Voxel[] voxels = new Voxel[2];
	
	/**
	 * Initializes {@link Voxel Voxels} to be used in the game.
	 */
	public void initializeVoxels() {
		SPACE = new Voxel(0, "space");
		STONE = new Voxel(1, "stone");
		

		voxels[0] = SPACE;
		voxels[1] = STONE;
	}
}
