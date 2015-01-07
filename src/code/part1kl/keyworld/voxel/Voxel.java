package code.part1kl.keyworld.voxel;

/**A single unit in the game. Comprised of {@link code.part1kl.keyworld.material.Material Materials} that determine it's color and properties
 * 
 * @author part1kl
 *
 */
public class Voxel {
//TODO: add mass and other attributes
	
	private int ident;
	
	private float mass;
	private int color;
	private byte[] matTypes;
	private int metadata;
	
	public Voxel(int id) {
		matTypes = new byte[64];
	}
	
	/**Voxel identifier (based on type)*/
	public int ID() { return ident; }
}
