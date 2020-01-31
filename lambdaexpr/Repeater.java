// Peter Idestam-Almquist, 2017-02-26.
// Functional interface.

package peteria.javaconcurrency.lambdaexpr;

@FunctionalInterface
interface Repeater {
	void repeat(int count);
}
