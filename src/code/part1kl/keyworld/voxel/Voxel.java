package code.part1kl.keyworld.voxel;

import code.part1kl.keyworld.utils.Float3;
import code.part1kl.keyworld.world.World;

/**A single unit in the game. Comprised of {@link code.part1kl.keyworld.material.Material Materials} that determine it's color and properties
 * 
 * @author part1kl
 *
 */
public class Voxel {
//TODO: add mass and other attributes
	
	public float mass;
	public Float3 color;
	public byte[] matTypes;
	public int metadata;
	
	public Voxel(byte[] materials) {
		matTypes = materials;
		createColor();
	}
	
	public void createColor() {
		float a=0;
		for(int i=0; i<64; i++) {
			a += World.materials[matTypes[i]].COLOR.a;
		}
		System.out.println(a);
		a = a/64f;
		float b=0;
		for(int i=0; i<64; i++) {
			b += World.materials[matTypes[i]].COLOR.b;
		}
		b = b/64f;
		float c=0;
		for(int i=0; i<64; i++) {
			c += World.materials[matTypes[i]].COLOR.c;
		}
		c = c/64f;
		System.out.println(matTypes[0]+"-");
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		color = new Float3(a, b, c);
	}
}
