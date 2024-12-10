package Lang.Model.Structures;

public class BinaryTree <T>{
    private final String token;
    private final BinaryTree<T> left;
    private final BinaryTree<T> right;

    private boolean isLeaf() {
        return left == null && right == null;
    }

    public BinaryTree(T program) {
        token = program.toString();
        left = null;
        right = null;
    }

    public BinaryTree(T program, BinaryTree<T> l,BinaryTree<T> r){
        token = program.toString();
        left = l;
        right = r;
    }
    public StringBuilder traversal() {
        StringBuilder stringBuilder = new StringBuilder();

        if (left != null) {
            stringBuilder.append(left.traversal());
        }

        if (isLeaf()) {
            stringBuilder.append(token).append('\n');
        }

        if (right != null) {
            stringBuilder.append(right.traversal());
        }

        return stringBuilder;
    }
}
