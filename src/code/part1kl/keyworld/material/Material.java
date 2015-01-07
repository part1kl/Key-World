package code.part1kl.keyworld.material;

/**Materials hold properties that define how {@link code.part1kl.keyworld.voxel.Voxel Voxels} interact and behave
 * 
 * @author part1kl
 *
 */
public class Material {

	private int ident;
	public float MASS;
	public String NAME;
	public boolean FLAMMABLE;
	public boolean ORE;
	public boolean NATURAL;
	
	public Material(int id, float mass, String name, boolean flammable, boolean ore, boolean natural) {
		ident = id;
		MASS = mass;
		NAME = name;
		FLAMMABLE = flammable;
		ORE = ore;
		NATURAL = natural;
	}
}
