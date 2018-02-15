// The test harness will belong to the following package; the BST
// implementation will belong to it as well. In addition, the BST
// implementation will specify package access for the inner node class
// and all data members in order that the test harness may have access
// to them.
//
package CS3114.J2.DS;

import java.util.LinkedList;

//Project 2 for CS 3114 Spring 2018
//
//Programmer:    Onat Calik
//Last modified: February 8, 2018
//
// BST<> provides a generic implementation of a binary search tree
//
// BST<> implementation constraints:
// - The tree uses package access for root, and for the node type.
// - The node type uses package access for its data members.
// - The tree never stores two objects for which compareTo() returns 0.
// - All tree traversals are performed recursively.
// - Optionally, the BST<> employs a pool of deleted nodes.
// If so, when an insertion is performed, a node from the pool is used
// unless the pool is empty, and when a deletion is performed, the
// (cleaned) deleted node is added to the pool, unless the pool is
// full. The maximum size of the pool is set via the constructor.
//
// User data type (T) constraints:
// - T implements compareTo() and equals() appropriately
// - compareTo() and equals() are consistent; that is, compareTo()
// returns 0 in exactly the same situations equals() returns true
//


//On my honor:
//
//- I have not discussed the Java language code in my program with
//anyone other than my instructor or the teaching assistants
//assigned to this course.
//
//- I have not used Java language code obtained from another student,
//or any other unauthorized source, including the Internet, either
//modified or unmodified. 
//
//- If any Java language code or documentation used in my program
//was obtained from another source, such as a text book or course
//notes, that has been clearly noted with a proper citation in
//the comments of my program.
//
//- I have not designed this program in such a way as to defeat or
//interfere with the normal operation of the supplied grading code.
//
//<Onat Calik>
//<onatc6>
public class BST<T extends Comparable<? super T>> {
	 class BinaryNode {
		 // Initialize a childless binary node.
		 // Pre: elem is not null
		 // Post: (in the new node)
		 // element == elem
		 // left == right == null
		 public BinaryNode( T elem ) { 
			 element = elem;
			 left = null;
			 right = null;
		 }
		 // Initialize a binary node with children.
		 // Pre: elem is not null
		 // Post: (in the new node)
		 // element == elem
		 // left == lt, right == rt
		 public BinaryNode( T elem, BinaryNode lt, BinaryNode rt ) { 
			 element = elem;
			 left = lt;
			 right = rt;
		 }
			 T element; // the data in the node
			 BinaryNode left; // pointer to the left child
			 BinaryNode right; // pointer to the right child
	 }
	 
	 //LOCAL VARIABLES
	 BinaryNode root; // pointer to root node, if present
	 BinaryNode pool; // pointer to first node in the pool
	 int pSize; // size limit for node pool
	 private boolean insertSuccess;	//returns true if insert is successful
	 private boolean removeSuccess;	//returns true if remove is successful
	 LinkedList<BinaryNode> poolList;
	 
	 // Initialize empty BST with no node pool.
	 // Pre: none
	 // Post: (in the new tree)
	 // root == null, pool == null, pSize = 0
	 public BST( ) { 
		 root = null;
		 pool = null;
		 pSize = 0;
		 poolList = new LinkedList<BinaryNode>();
	 }
	 // Initialize empty BST with a node pool of up to pSize nodes.
	 // Pre: none
	 // Post: (in the new tree)
	 // root == null, pool = null, pSize == Sz
	 public BST( int Sz ) { 
		 root = null;
		 pool = null;
		 pSize = Sz;
		 poolList = new LinkedList<BinaryNode>();
	 }
	// Return true iff BST contains no nodes.
	 // Pre: none
	 // Post: the binary tree is unchanged
	 public boolean isEmpty( ) { 
		 if (root == null) {
			 return true;
		 }
		 return false;
	 }
	// Return pointer to matching data element, or null if no matching
	 // element exists in the BST. "Matching" should be tested using the
	 // data object's compareTo() method.
	 // Pre: x is null or points to a valid object of type T
	 // Post: the binary tree is unchanged
	 public T find( T x ) { 
		 
		 return find(x, root);
	 }
	 
	 private T find(T x, BinaryNode sRoot) {
		 if(sRoot == null) {
			 return null;
		 }
		 int compareResult = x.compareTo(sRoot.element);
		 if(compareResult < 0) {
			 return find(x, sRoot.left);
		 }
		 else if(compareResult > 0) {
			 return find(x, sRoot.right);
		 }
		 else {
			 return sRoot.element;
		 }
	 }
	 
	 // Insert element x into BST, unless it is already stored. Return true
	 // if insertion is performed and false otherwise.
	 // Pre: x is null or points to a valid object of type T
	 // Post: the binary tree contains x
	 public boolean insert( T x ) {
		 
		insertSuccess = true;
		root = insert(x, root);
		return insertSuccess;

	 }
	 
	 private BinaryNode insert( T x, BinaryNode sRoot) {
		 if(sRoot == null) {
			 if(poolList.size() == 0) { return new BinaryNode(x, null, null); }
			 else {
				 BinaryNode temp = poolList.removeFirst();
				 temp.element = x;
				 temp.left = null;
				 temp.right = null;
				 pool = poolList.peek();
				 return temp;
			 }
		 }
		 int compareResult = x.compareTo(sRoot.element);
		 if(compareResult < 0) {
			 sRoot.left = insert(x, sRoot.left);
		 }
		 else if (compareResult > 0) {
			 sRoot.right = insert(x, sRoot.right);
		 }
		 else {
			 insertSuccess = false;
		 }
		 
		 return sRoot;
	 }
	 
	 // Delete element matching x from the BST, if present. Return true if
	 // matching element is removed from the tree and false otherwise.
	 // Pre: x is null or points to a valid object of type T
	 // Post: the binary tree does not contain x
	 public boolean remove( T x ) { 
		 removeSuccess = true;
		 root = remove(x, root);
		 return removeSuccess;
	 }
	 
	 private BinaryNode removeRightMin(BinaryNode smallNode, BinaryNode parent) {
		 if (smallNode == null) {
			 return null;
		 }
		 if (smallNode.left == null) {
			 
			 //tree structure is destroyed if the node we are trying to delete is the root and this line is performed
			 if(parent != root) {
				 parent.left = smallNode.right;
			 }

			 return smallNode;
			 
		 }
		 
		 return removeRightMin(smallNode.left, smallNode);
	 }
	 
	 private BinaryNode remove(T x, BinaryNode sRoot) {
		 if (sRoot == null) {
			 removeSuccess = false;
			 return null;
		 }
		 
		 int compareResult = x.compareTo(sRoot.element);
		 
		 if(compareResult < 0) {
			 sRoot.left = remove(x, sRoot.left);
		 }
		 else if (compareResult > 0) {
			 sRoot.right = remove(x, sRoot.right);
		 }
		 
		 else {
			 if (poolList.size() < pSize) {
				 BinaryNode temp = new BinaryNode(x, null, null);
				 poolList.add(temp);
				 pool = poolList.peek();
			 }
			 //2 nonempty children
			 if (sRoot.left != null && sRoot.right != null) {
				 
				 BinaryNode left = sRoot.left;		//save left subtree
				 BinaryNode right = sRoot.right;	//save right subtree
				 sRoot = removeRightMin(sRoot.right, sRoot);	//removed Node is now equal to the smallest node in the right subtree
				 
				 BinaryNode removeRight = sRoot.right;		//right node of the removed node
				 
				 sRoot.left = left;		//Restore old left node of the removed node
				 sRoot.right = right;	//Restore old right node of the removed node
				 
				 //If right node is the smallest, this fix it so that new node and it's right child aren't the same nodes
				 if(sRoot.element == sRoot.right.element) {
					 
					 sRoot.right = removeRight;
				 }	 
			 }
			 
			 //2 empty children
			 else if (sRoot.left == null && sRoot.right == null) {
				 sRoot = null;
			 }
			 
			 //left child is not empty
			 else if (sRoot.left != null) {
				 sRoot = sRoot.left;
			 }
			 
			 //right child is not empty
			 else if(sRoot.right != null) {
				 sRoot = sRoot.right;
			 }
		 }
		 
		 return sRoot;
	 }
	 // Remove from the tree all values y such that y > x, according to
	 // compareTo().
	 // Pre: x is null or points to a valid object of type T
	 // Post: the tree contains no value y such that compareTo()
	 // indicates y > x
	 public void capWith( T x ) {
		 
		 root = capWith(x, root);
		 
	 }
	 
	 //doesnt work yet
	 private BinaryNode capWith(T x, BinaryNode sRoot) {
		 
		 if (sRoot == null) {
			 return null;
		 }
		 
		 int compareResult = x.compareTo(sRoot.element);
		 
		 if(compareResult < 0) {
			 if (sRoot.left != null && sRoot.right != null) {
				 
				 BinaryNode left = sRoot.left;		//save left subtree
				 BinaryNode right = sRoot.right;	//save right subtree
				 sRoot = removeRightMin(sRoot.right, sRoot);	//removed Node is now equal to the smallest node in the right subtree
				 
				 BinaryNode removeRight = sRoot.right;		//right node of the removed node
				 
				 sRoot.left = left;		//Restore old left node of the removed node
				 sRoot.right = right;	//Restore old right node of the removed node
				 
				 //If right node is the smallest, this fix it so that new node and it's right child aren't the same nodes
				 if(sRoot.element == sRoot.right.element) {
					 
					 sRoot.right = removeRight;
				 }	 
			 }
			 
			 //2 empty children
			 else if (sRoot.left == null && sRoot.right == null) {
				 sRoot = null;
			 }
			 
			 //left child is not empty
			 else if (sRoot.left != null) {
				 sRoot = sRoot.left;
			 }
			 
			 //right child is not empty
			 else if(sRoot.right != null) {
				 sRoot = sRoot.right;
			 }
			 
			 sRoot = capWith(x, sRoot);
		 }
		 
		 else {
			 sRoot.left = capWith(x, sRoot.left);
			 sRoot.right = capWith(x, sRoot.right);
		 }
		 
		 return sRoot;
	 }
	 
	 // Return true iff other is a BST that has the same physical structure
	 // and stores equal data values in corresponding nodes. "Equal" should
	 // be tested using the data object's equals() method.
	 // Pre: other is null or points to a valid BST<> object, instantiated
	 // on the same data type as the tree on which equals() is invoked
	 // Post: both binary trees are unchanged
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) { 
		 
		try {
			BST<T> other_BST = (BST<T>) other;
			 return equals(root, other_BST.root);
		}
		catch(Exception e) {
			return false;
		}
		 
	 }
	 
	private boolean equals(BinaryNode root1, BinaryNode root2) {
		 if (root1 == null && root2 == null) {return true;}
		 else if(root1 == null || root2 == null) {return false;}
		 else {
			 boolean elem = root1.element.equals(root2.element);
			 boolean left = equals(root1.left, root2.left);
			 boolean right = equals(root1.right, root2.right);
			 
			 return (elem && left && right);
		 }
		 
	 }
	 
	 
	 
}
