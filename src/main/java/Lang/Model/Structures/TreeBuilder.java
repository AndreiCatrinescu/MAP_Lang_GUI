package Lang.Model.Structures;

import Lang.Model.Statements.Statement;

public interface TreeBuilder<T> {
    BinaryTree<T> buildTree(Statement program);
}
