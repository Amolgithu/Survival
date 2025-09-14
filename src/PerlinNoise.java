import java.util.Random;

/**
 * A utility class for generating Perlin noise.
 * This implementation is based on Ken Perlin's "Improved Noise" algorithm.
 */
public final class PerlinNoise {

    private static final int[] p = new int[512];

    static {
        // Initialize with values 0-255
        int[] permutation = new int[256];
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        Random rand = new Random(100); // Using a seed makes the noise repeatable
        for (int i = 255; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = permutation[i];
            permutation[i] = permutation[index];
            permutation[index] = temp;
        }

        for (int i = 0; i < 256; i++) {
            p[i] = p[i + 256] = permutation[i];
        }
    }

    /**
     * Calculates 3D Perlin noise for the given coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     * @return A noise value between -1.0 and 1.0.
     */
    public static double noise(double x, double y, double z) {
        // Find the unit cube that contains the point
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        int Z = (int) Math.floor(z) & 255;

        // Find relative x, y, z of point in cube
        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        // Compute fade curves for each of x, y, z
        double u = fade(x);
        double v = fade(y);
        double w = fade(z);

        // Hash coordinates of the 8 cube corners
        int A = p[X] + Y;
        int AA = p[A] + Z;
        int AB = p[A + 1] + Z;
        int B = p[X + 1] + Y;
        int BA = p[B] + Z;
        int BB = p[B + 1] + Z;

        // Add blended results from 8 corners of the cube
        double res = lerp(w, lerp(v, lerp(u, grad(p[AA], x, y, z), grad(p[BA], x - 1, y, z)),
                                     lerp(u, grad(p[AB], x, y - 1, z), grad(p[BB], x - 1, y - 1, z))),
                             lerp(v, lerp(u, grad(p[AA + 1], x, y, z - 1), grad(p[BA + 1], x - 1, y, z - 1)),
                                     lerp(u, grad(p[AB + 1], x, y - 1, z - 1), grad(p[BB + 1], x - 1, y - 1, z - 1))));
        return (res + 1) / 2; // Return value in range [0, 1] for easier color mapping
    }

    // Fade function as defined by Ken Perlin: 6t^5 - 15t^4 + 10t^3
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Linear interpolation
    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    // Calculates the dot product of a randomly selected gradient vector and the 8 location vectors.
    private static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}