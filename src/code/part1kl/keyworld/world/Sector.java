package code.part1kl.keyworld.world;

/**
 * 
 * @author part1kl
 *
 */
public class Sector {

	/**The size of Sectors in {@link code.part1kl.keyworld.voxel.Voxel Voxels}*/
	public final int SIZE = 20;
	//TODO: Replace voxles array with something better using Point3D
	/**Holds all Voxels in the sector
	 */
	private int[] voxels;
	
	public Sector() {
		voxels = new int[20*20*20];
		voxels[0]=1;
	}
}
