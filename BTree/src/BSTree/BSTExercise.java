package BSTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A basic Binary Search Tree class.
 * 
 * @author Katie Chao
 *
 * @param <Item>
 *            The type of item of the node. Must be Comparable
 */
public class BSTExercise<Item extends Comparable<Item>> {

	public static final int RAND_INT_BOUND = 300;	// bound for random items generated
	public static Random rand = new Random();		// random number generator

	private Node root;		// root of BST ... null until first insert

	/**
	 * Nested subclass defining the nodes in the tree, with an item, left and right
	 * subtrees, and number of nodes in subtree
	 *
	 * Notes:
	 *	1. if the Item in a node has value x
	 *	   All items in the left sub-tree have values < x
	 *     All items in the right sub-tree have values > x
	 *  2. If there are no items less than (or greater than) x,
	 *     the x-node will have a left (or right) field of null
	 */
	private class Node {
		private Item item;			// sorted by item
		private Node left;			// left sub-tree (or null)
		private Node right;			// right sub-tree (or null)

		public Node(Item item) {
			this.item = item;
			this.left = null;
			this.right = null;
		}

		public String toString() {
			return this.item.toString();
		}
	}	// end of class Node

	/**
	 * Returns the size of the tree
	 * 
	 * @return the number of nodes in the entire tree
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Size of the sub-tree rooted at x
	 * 
	 * @param x  root of the tree to be sized
	 * @return number of nodes in the sub-tree
	 */

	private int size(Node x) {
		if (x == null) {
			return 0;
		}
		return 1 + size(x.left) + size(x.right);
	}

	/**
	 * search the tree for a desired node
	 *
	 * @param Node  node to compare its item with given item
	 * @param item  item to search for
	 * @return  existing tree node with desired item (or null)
	 */
	public Node locate(Item item) {
		return locate(root, item);
	}

	/**
	 * search a sub-tree tree for a desired node
	 *
	 * @param Node  node to compare its item with given item
	 * @param item  item to search for
	 * @return  existing tree node with desired item (or null)
	 */
	private Node locate(Node x, Item item) {

		if (x == null) {
			return null;
		}

		if (x.item.compareTo(item) == 0) {
			return x;
		}

		if (x.left != null) {
			return locate(x.left, item);
		}

		if (x.right != null) {
			return locate(x.right, item);
		}

		return null;

	}

	/**
	 * Insert an item into the tree
	 * 
	 * @param item  item to add
	 */
	public void insert(Item item) {
		Node n = insert(root, item);
		if (root == null)
			root = n;
	}

	/**
	 * insert an item into its proper place in a sub-tree
	 * 
	 * @param x  node below which item should be inserted
	 *			 (for first insert (x==null) return new node
	 *			  which will become the root of the tree)
	 * @param item to be inserted insert
	 * @return node for the new (or preexisting) item
	 *
	 * NOTE: attempting to insert an item already in the tree
	 *       will simply return the already-existing node
	 */
	private Node insert(Node x, Item item) {

		if (x == null) {
			Node n = new Node(item);
			return n;
		} else if (item.compareTo(x.item) == 0) {
			return x;
		} else if (item.compareTo(x.item) < 0) {
			if (x.left != null) {
				return insert(x.left, item);
			} else {
				Node n = new Node(item);
				x.left = n;
				return x.left;
			}
		} else if (item.compareTo(x.item) > 0) {
			if (x.right != null) {
				return insert(x.right, item);
			} else {
				Node n = new Node(item);
				x.right = n;
				return x.right;
			}
		}
		return null;
	}

	/**
	 * Returns the height of the BT.
	 *
	 * @return the height of the BT
	 *			the height of the tree is a count of the
	 *			longest path edges (rather than nodes).
	 *			Thus the height of a one-node tree is 0.
	 */
	public int height() {
		return height(root);
	}

	/**
	 * return (deepest) height of a specified sub-tree
	 * 
	 * @param x root of sub-tree to be measured
	 * @return height (a 1-node tree has a height of 0)
	 */
	private int height(Node x) {

		int count = 0;

		if (x == null) {
			return 0;
		} 
		
		else if (x.left == null && x.right == null) {
			return 0;
		}

		else { 
			return Math.max(height(x.left) + 1, height(x.right) + 1);
		}
	}

	/**
	 * @return string representation of the BST
	 */
	public String toString() {
		return "<" + toString(root) + ">";
	}

	/**
	 * @param root of the sub-tree to be represented
	 * @return string representation of sub-tree
	 * 
	 * @return
	 */
	public String toString(Node root) {
		if (root == null)
			return "";
		else
			return toString(root.left) + " " + root.item + " " + toString(root.right);
	}

	/**
	 * test method ... construct a tree with specified number of nodes
	 *
	 * @param nodes desired number of (random value) nodes
	 * @return the constructed tree
	 */
	public static BSTExercise<Integer> constructIntTree(int nodes) {

		BSTExercise<Integer> root = new BSTExercise<Integer>();
		
		for (int count = 0; count < nodes; count++) {
			root.insert(rand.nextInt(RAND_INT_BOUND));
		}

		return root;
	}

	/**
	 * measurement method ... height of random trees
	 *
	 * @param number of trees to construct
	 * @param number of nodes in each tree
	 */
	public static void randomTreeHeights(int nodes, int trees) {

		int maxHeight = 0;
		int minHeight = 0;
		List<Integer> heightList = new ArrayList<Integer>();

		for (int i = 0; i < trees; i++) {
			int treeHeight = constructIntTree(nodes).height();
			if (treeHeight > maxHeight) {
				maxHeight = treeHeight;
			}
			if (treeHeight < minHeight) {
				minHeight = treeHeight;
			}
			heightList.add(treeHeight);
		}

		int listSum = 0;
		for (int j = 0; j < heightList.size(); j++) {
			listSum += heightList.get(j);
		}
		
		System.out.println("Maximum tree height: " + maxHeight);
		System.out.println("Minimum tree height: " + minHeight);
		System.out.println("Mean tree height: " + listSum/heightList.size());
		
		//TODO: caclulate and report theoretical maximum and minimum heights
			// Theoretical minimum - log2(n), where n = # of nodes in tree
			// Theoretical maximum - # of nodes - 1.
		
		//TODO: do your findings support that the average height is O(logn)?
			// Yes, because the left and right subtree of each node is of equal
			// size (otherwise the tree wouldn't be binary). So, each level
			// "doubles" in size a.k.a at a logarithmic rate.

		//TODO: what would it take to generate a best-case (shallowest) tree
			// You would need to use a sorting algorithm to ensure that the
			// middle remaining element is always placed as the root and then
			// recursively add the rest of the leaves using that same find-the-
			// middle technique. Then, the tree we end up with would have the
			// minimal possible height.
	}

	/**
	 * test suite orchestration
	 */
	public static void main(String args[]) {
		// create a tree, inserting nodes out of order
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		bt.insert(4);
		bt.insert(1);
		bt.insert(3);
		bt.insert(5);
		bt.insert(2);
		bt.insert(2);

		// extract and check information from the nodes
		String contents = bt.toString();
		if (contents.equals("< 1  2  3  4  5 >"))
			System.out.println(contents + " ... CORRECT");
		else
			System.out.println(contents + " ... ERROR, expected < 1  2  3  4  5 >");

		int size = bt.size();
		if (size == 5)
			System.out.println("size: " + size + " ... CORRECT");
		else
			System.out.println("size: " + size + " ... ERROR, expected 5");

		int height = bt.height();
		if (height == 3)
			System.out.println("height: " + height + " ... CORRECT");
		else
			System.out.println("height: " + height + " ... ERROR, expected 3");

		BSTExercise<Integer>.Node one = bt.locate(1);
		if (one.item == 1) 
			System.out.println("locate(1) finds node " + one.item + " ... CORRECT");
		else
			System.out.println("locate(1) returns " + one + " ... ERROR, expected 1");

		BSTExercise<Integer>.Node six = bt.locate(6);
		if (six == null) 
			System.out.println("locate(6) returns " + "null" + " ... CORRECT");
		else
			System.out.println("locate(6) returns " + six + " ... ERROR, expected null");


		// construct a 128 node tree
		BSTExercise<Integer> r = constructIntTree(128);
		System.out.println(r);

		// construct, measure and report on 100 128-node trees
		randomTreeHeights(128, 100);
	}
}
