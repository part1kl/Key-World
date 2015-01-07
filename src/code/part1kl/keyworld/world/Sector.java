package code.part1kl.keyworld.world;

import code.part1kl.keyworld.voxel.Voxel;

/**Container that holds {@link code.part1kl.keyworld.voxel.Voxels}. Synonymous with Minecraft "Chunks"
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
	private Voxel[] voxels;
	
	public Sector() {
		voxels = new Voxel[SIZE*SIZE*SIZE];
	}
}
