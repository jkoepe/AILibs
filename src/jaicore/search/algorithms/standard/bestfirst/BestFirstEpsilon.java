package jaicore.search.algorithms.standard.bestfirst;

import jaicore.search.algorithms.standard.core.NodeEvaluator;
import jaicore.search.algorithms.standard.core.ORGraphSearch;
import jaicore.search.structure.core.GraphGenerator;
import jaicore.search.structure.core.Node;

/**
 * A* algorithm implementation using the method design pattern.
 *
 * @author Felix Mohr
 */
public class BestFirstEpsilon<T, A> extends ORGraphSearch<T, A, BestFirstEpsilonLabel> {

	private final double epsilon;

	public BestFirstEpsilon(GraphGenerator<T, A> graphGenerator, NodeEvaluator<T, BestFirstEpsilonLabel> pNodeEvaluator, double epsilon) {
		super(graphGenerator, pNodeEvaluator);
		this.epsilon = epsilon;
	}

	@Override
	public Node<T, BestFirstEpsilonLabel> nextNode() {
		if (epsilon <= 0 || open.isEmpty())
			return open.poll();
		int best = open.peek().getInternalLabel().getF1();
		Node<T, BestFirstEpsilonLabel> choice = open.stream().filter(n -> n.getInternalLabel().getF1() <= best * (best >= 0 ? 1 + epsilon : 1 - epsilon))
				.min((p1, p2) -> p1.getInternalLabel().getF2() - p2.getInternalLabel().getF2()).get();
		open.remove(choice);
		return choice;
	}
}