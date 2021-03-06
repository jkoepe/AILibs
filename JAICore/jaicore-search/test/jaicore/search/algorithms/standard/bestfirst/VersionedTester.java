package jaicore.search.algorithms.standard.bestfirst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import jaicore.search.graphgenerators.npuzzle.standard.NPuzzleGenerator;
import jaicore.search.graphgenerators.npuzzle.standard.NPuzzleNode;
import jaicore.search.graphgenerators.nqueens.NQueenGenerator;
import jaicore.search.graphgenerators.nqueens.QueenNode;
import jaicore.search.structure.core.VersionedGraphGenerator;
import jaicore.search.structure.core.VersionedDomainNode;

public class VersionedTester {

	@Test
	public void versionedNQueenTest() {
		System.out.println("Testing Versioning with NQueens.");

		int[] numbersOfSolutions = { 2, 10, 4, 40, 92 };

		for (int i = 0; i < numbersOfSolutions.length; i++) {
			int n = i + 4;
			System.out.println("Checking " + n + "-Queens Problem ... ");
			NQueenGenerator gen = new NQueenGenerator(n);
			VersionedGraphGenerator vgen = new VersionedGraphGenerator(gen);

			// Redoing the sequential test from NQueenTester with versioned nodes
			BestFirst<VersionedDomainNode<QueenNode>, String> search = new BestFirst<>(vgen,
					node -> (double) ((VersionedDomainNode<QueenNode>) node.getPoint()).getNode().getNumberOfAttackedCellsInNextRow());
			int solutions = 0;
			while (search.nextSolution() != null)
				solutions++;
			assertEquals(numbersOfSolutions[i], solutions);

			// print out the ids of the solution path
			System.out.println("The ids of a solution path");
			search = new BestFirst<>(vgen, node -> (double) ((VersionedDomainNode<QueenNode>) node.getPoint()).getNode().getNumberOfAttackedCellsInNextRow());
			List<VersionedDomainNode<QueenNode>> solution = search.nextSolution();
			solution.stream().forEach(node -> System.out.println("ID: " + node.getId()));
			solution.stream().forEach(node -> assertTrue(node.getId() > 0));

			// testing if the ids are -1 if nodenumbering is disabled
			System.out.println("Testing if every id is -1, if nodenumbering is disabled.");
			vgen.setNodeNumbering(false);
			solution.clear();
			search = new BestFirst<>(vgen, node -> (double) ((VersionedDomainNode<QueenNode>) node.getPoint()).getNode().getNumberOfAttackedCellsInNextRow());
			solution = search.nextSolution();
			solution.stream().forEach(node -> System.out.println(node.getId()));
			solution.stream().forEach(node -> assertEquals(-1, node.getId()));

			System.out.println("done");

		}

	}

	@Test
	public void versionedNpuzzleTest() {
		System.out.println("Testing Versioning with NPuzzle.");

		// Test if the normal 8-Puzzle works with versioning
		// first group test with enabled ids
		VersionedGraphGenerator gen = new VersionedGraphGenerator(new NPuzzleGenerator(8, 8));
		System.out.println("Test with ids enabled");
		BestFirst<VersionedDomainNode<NPuzzleNode>, String> search = new BestFirst<>(gen, node -> ((VersionedDomainNode<NPuzzleNode>) node.getPoint()).getNode().getDistance());
		List<VersionedDomainNode<NPuzzleNode>> solution = search.nextSolution();
		solution.stream().forEach(node -> assertTrue(node.getId() > 0));
		System.out.println("Every node on the solution path got a id");

		// second group test with disabled ids
		System.out.println("Test with ids disabled");
		gen.setNodeNumbering(false);
		search = new BestFirst<>(gen, node -> ((VersionedDomainNode<NPuzzleNode>) node.getPoint()).getNode().getDistance());
		solution = search.nextSolution();
		solution.stream().forEach(node -> assertEquals(-1, node.getId()));

		System.out.println("Every node got the id -1");

		System.out.println("done");

	}

}
