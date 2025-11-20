public class RBT<T extends Comparable<T>> {
    public Nodo<T> root;
    public int size;
    public RBT() {
        this.root = null;
        this.size = 0;
    }

    public void insert(T elemento) {
        Nodo<T> newNode = new Nodo<>(elemento);
        size++;
        if (root == null) {
            root = newNode;            
            return;
        } 
        Nodo<T> actual = root;
        while (true) {
            if (actual.elemento.compareTo(elemento) > 0) {
                if (actual.left == null) {
                    actual.left = newNode;
                    newNode.parent = actual;
                    break;
                }
                actual = actual.left;
            } else {
                if (actual.right == null) {
                    actual.right = newNode;
                    newNode.parent = actual;
                    break;
                }
                actual = actual.right;
            }
        }        
    }

    public void inOrder(){
        if(root==null){
            System.out.println("El arbol esta vacio");
            return;
        }
        inOrderRecursivo(root);
    }

    public void inOrderRecursivo(Nodo<T> nodo){
        if(nodo.left != null){
            inOrderRecursivo(nodo.left);
        }
        System.out.println(nodo.elemento);
        if(nodo.right != null){
            inOrderRecursivo(nodo.right);
        }
    }
}