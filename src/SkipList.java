import java.util.Comparator;

public class SkipList<L> {
	public SkipNode<L> head;
	private Comparator<L> comparator;
	private double p = 0.5;
        private int level;
        private int maxLevel;
        private SkipList<L> justAbove;
	public SkipList<L> upperList;
	
	public SkipList(Comparator<L> comparator, int maxLevel,double p) {
		super();
		this.head = null;
		this.comparator = comparator;
		this.level=maxLevel;
		this.maxLevel=maxLevel;
		this.p=p;
		if(maxLevel>0){
			justAbove=new SkipList<L>(comparator,maxLevel-1,p);
		}else{
			justAbove=null;
		}
		upperList=getUpperList();
	}
	
	public boolean insertNode(SkipNode<L> node){		
		//recherche
		if(head==null){
			head=new SkipNode<L>(null,node.getUnder(),node.getValue(),level);
			if(level>0){
				justAbove.insertNode(new SkipNode<L>(null, head, node.getValue(),level-1));
			}
		}else{ //si valeur plus petite que la tête
			SkipNode<L> currentNode = head; //on part de la tête
			if(comparator.compare(currentNode.getValue(),node.getValue()) >= 0){ //si valeur plus petite que la tête
				node.setNext(head);
				node.setUnder(node.getUnder());
				head=node;
				if(level > 0){
					justAbove.insertNode(new SkipNode<L>(null, head, node.getValue(),level-1));
				}
			}else{		
				//recherche
				currentNode=searchNode(node.getValue(),true);
				if(currentNode.getValue().equals(node.getValue())){
					return false; //pas besoin d'insérer
				}
				//insertion dans le niveau le plus bas
				if(currentNode.getNext()==null && comparator.compare(currentNode.getValue(),node.getValue()) < 0){ //pas de noeud après
					currentNode.setNext(new SkipNode<L>(null,node.getUnder(),node.getValue(),level));
					//proba
					double random=Math.random();
					if(random < p && level != 0){ //alors le noeud insérer appartient aussi à la couche i+1
						justAbove.insertNode(new SkipNode<L>(null, currentNode.getNext(), node.getValue(),level-1));//on insére dans la couche au dessus
					}
				}else{ // un noeud après
					SkipNode<L> newNode = new SkipNode<L>(currentNode.getNext(),node.getUnder(),node.getValue(),level);
					currentNode.setNext(newNode);
					//proba
					double random=Math.random();
					if(random < p && level != 0){ //alors le noeud insérer appartient aussi à la couche i+1
						justAbove.insertNode(new SkipNode<L>(null,newNode,node.getValue(),level-1));//on insére dans la couche au dessus
					}
				}
			}
		}
		return true;
	}
	
	
	public void showSkipList(){
		if(upperList.head==null){
			System.out.println("SkipList vide");
		}else{
			if(level>0){
				justAbove.showSkipList();
			}		
			SkipNode<L> currentNode = head; //on part de la tête
			System.out.print("niveau "+level+": ");
			while(currentNode!=null){
				System.out.print(currentNode.getValue()+"->");
				currentNode=currentNode.getNext();
			}
			System.out.println("tail");
		}
	}
	
	public boolean removeNode(L value){
		SkipNode<L> currentNode = upperList.head;
		SkipNode<L> previousNode = upperList.head;
		while(comparator.compare(currentNode.getValue(),value) != 0){
				if(comparator.compare(currentNode.getValue(),value) < 0 && currentNode.getNext()!=null){
					previousNode=currentNode;
					currentNode=currentNode.getNext();
				}else if(currentNode.getLevel()!=maxLevel){
					currentNode=previousNode.getUnder();
					previousNode=currentNode;
				}else{
					//élément non présent
					return false;
				}
			}
		previousNode.setNext(currentNode.getNext());
		while(currentNode.getUnder()!=null){
			currentNode=previousNode.getUnder();  //on part du noeud précédent 
			previousNode=previousNode.getUnder();
			while(comparator.compare(currentNode.getValue(),value) != 0){
				previousNode=currentNode;
				currentNode=currentNode.getNext();
			}
			previousNode.setNext(currentNode.getNext());
		}
	//	this.removeNode(value);
		return true;
	}
	
	public SkipNode<L> searchNode(L value,boolean prec){	
		SkipNode<L> currentNode = upperList.head;
		SkipNode<L> previousNode = upperList.head;
		while(comparator.compare(currentNode.getValue(),value) != 0){
		//	System.out.println("currentNodeValue("+currentNode.getLevel()+"):"+currentNode.getValue()+" valeur recherchée :"+value);
			if(comparator.compare(currentNode.getValue(),value) < 0 && currentNode.getNext()!=null){
				previousNode=currentNode;
				currentNode=currentNode.getNext();
			}else if(currentNode.getLevel()!=maxLevel){
				currentNode=previousNode.getUnder();
				previousNode=currentNode;
			}else{
				if(prec){
					if(comparator.compare(currentNode.getValue(),value) < 0){
						return currentNode;
					}
					return previousNode;
				}else{
					return null;
				}
			}
		}
		return currentNode;
	}
	
	
	public long searchNodeCounter(L value,boolean prec){
		long i = 0;	
		SkipNode<L> currentNode = upperList.head;
		SkipNode<L> previousNode = upperList.head;
		while(comparator.compare(currentNode.getValue(),value) != 0){
			i++;
		//	System.out.println("currentNodeValue("+currentNode.getLevel()+"):"+currentNode.getValue()+" valeur recherchée :"+value);
			if(comparator.compare(currentNode.getValue(),value) < 0 && currentNode.getNext()!=null){
				previousNode=currentNode;
				currentNode=currentNode.getNext();
				
			}else if(currentNode.getLevel()!=maxLevel){
				currentNode=previousNode.getUnder();
				previousNode=currentNode;

			}else{
				if(prec){
					if(comparator.compare(currentNode.getValue(),value) < 0){
						return i;
					}
					return i;
				}else{
					return i;
				}
			}
		}
		return i;
	}

	private SkipList<L> getUpperList() {
		SkipList<L> upperList=this;
		while(upperList.justAbove!=null){
			upperList=justAbove.getUpperList();
		}
		return upperList;
	}	
} 
