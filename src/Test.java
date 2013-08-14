import java.util.*;

public class Test {
	
	public static void main(String[] args){
		//Création des SkipList
		long MoyresInsertions=0;
		long MoyresRecherches=0;
		long MoyresSuppression=0;
		int nbessais=1;
		int nblevelskiplist = 5;
		int nboperation =10; 
		boolean affiche = true;
		boolean mode = true; //true:interractif / false:batch
		double proba = 0.5;
		
		Scanner scanner = new Scanner (System.in);
		try {
		     	 System.out.print("\nEntrez la probabilite P de la SkipList (utilisez la virgule) : ");
     			 proba = scanner.nextFloat ();
			 if(proba < 0. || proba > 1.){
			    throw new InputMismatchException(" la valeur doit etre un float >= 0. et <= 1.");
			 }
     			 System.out.println("	-> SkipList a une probabilite P de "+proba);		
		
     			 System.out.print("\nEntrez le nombre de niveau de la SkipList en plus du niveau de base: ");
     			 nblevelskiplist = scanner.nextInt ();
			 if(nblevelskiplist<0){
			    throw new InputMismatchException(" la valeur doit etre un entier >= 0");
			 }
     			 System.out.println("	-> SkipList a "+(nblevelskiplist+1)+" niveau(x)\n");
			 
			 
			 System.out.print("Mode d'execution (interactif(visualisation pas a pas)/batch(calcul de moyennes) true/false) :");	
			 mode = scanner.nextBoolean();			  
			 System.out.println ("	-> Mode : "+mode+"\n");
			 
			 SkipList<Number> skipListNumber=new SkipList<Number>(new NumberComparator(),nblevelskiplist,proba);
			 
			 if(mode){
			 	//interactif
			 	boolean fin = false;
				String operation;
				while(!fin){
					System.out.print("Entrez l'operation a effectuer sur la SkipList,\n I:inserer,R:supprimer,S:recherche, X:fin du programme ");
					operation = scanner.next();					
					
					int value;
					boolean retour_operation;
					switch(operation.toLowerCase().charAt(0)){
						case 'i':
							System.out.println ("	-> Operation : Insertion\n");	
							System.out.print("Entrez l'entier a inserer dans la SkipList :");
							value = scanner.nextInt();
							SkipNode<Number> newNode =new SkipNode<Number>(null, null,value,nblevelskiplist);
							retour_operation = skipListNumber.insertNode(newNode);	
							System.out.println("\nEtat de la SkipList :");						
							skipListNumber.showSkipList();
							if(retour_operation){
								System.out.println("\nInsertion effectuee avec succes\n");
							}else{
								System.out.println("\nCle deja presente\n");
							}
						break;
						case 'r':
							System.out.println ("	-> Operation : Recherche\n\nEtat de la SkipList :");	
							System.out.print("Entrez l'entier a supprimer dans la SkipList :");
							value = scanner.nextInt();
							retour_operation = skipListNumber.removeNode(value);
							System.out.println("\nEtat de la SkipList :");
							skipListNumber.showSkipList();
							if(retour_operation){
								System.out.println("\nSuppression effectuee avec succes\n");
							}else{
								System.out.println("\nCle non presente\n");
							}
						break;
						case 's':
							System.out.println ("	-> Operation : Suppression\n\nEtat de la SkipList :");
							System.out.print("Entrez l'entier a rechercher dans la SkipList :");
							value = scanner.nextInt();
							retour_operation = (skipListNumber.searchNode(value,false)==null);
							System.out.println("\nEtat de la SkipList :");
							skipListNumber.showSkipList();
							if(retour_operation){
								System.out.println("\nElement non present\n");
							}else{
								System.out.println("\nRecherche effectuee avec succes: Cle = "+value+"\n");
							}
						break;
						case 'x':
							fin=true;
						break;
						default: throw new InputMismatchException("operateur invalide");
					}
				}
			 }else{ 			 
			 
				 //batch 
	     			 System.out.print("Entrez le nombre d'element (aleatoire) a generer dans la SkipList: ");
     				 nboperation = scanner.nextInt ();	
				 if(nboperation<0){
				    throw new InputMismatchException(" la valeur doit etre un entier >= 0");
				 }
				 System.out.println ("	-> SkipList a "+nboperation+" element(s)\n");	
			 
				/* System.out.print("Entrez le nombre de test a effectuer :\nrepetition de la configuration entree ci-dessus n fois,\npour le calcul des moyennes : ");
     				 nbessais = scanner.nextInt ();
			 	 if(nbessais<0){
			    	    throw new InputMismatchException(" la valeur doit etre un entier >= 0");
				 }
				 System.out.println ("	-> "+nbessais+" execution(s)\n"); */
				 
			 	System.out.print("Affichez les SkipList a chaque test (true/false) ? : ");
			 	affiche = scanner.nextBoolean();			  
			 	System.out.println ("	-> Affichage : "+affiche+"\n");
				
				
				for(int j=0;j<nbessais;j++){					
				//	ArrayList<Number> versus = new ArrayList<Number>();
					long resInsertions=0;
					long resRecherches=0;
					long resSuppression=0;
			
					//head de la SkipList
					skipListNumber.insertNode(new SkipNode<Number>(null, null,-9999,nblevelskiplist));
			
					//pour calculer le temps des différentes opérations
					long time1;
					long time2;
			
					//insertion des nombres + temps d'insertion
				for(int i=0;i<nboperation;i++){
						SkipNode<Number> newNode =new SkipNode<Number>(null, null,Math.round(Math.random()*1000000),nblevelskiplist);
						//SkipNode<Number> newNode =new SkipNode<Number>(null, null,i,nblevelskiplist);
				
					//	time1 = System.currentTimeMillis();	
					//	versus.add(x);
						skipListNumber.insertNode(newNode);
					//	time2 = System.currentTimeMillis();
				
					//	resInsertions += (time2-time1);
				}
			
					//affichage des skipList
				if(affiche){
					skipListNumber.showSkipList();
					System.out.println();
				}
				
				//recherche des nombres + temps de recherche
				for(int i=0;i<nboperation;i++){
					long m = 0;				
				//	time1 = System.currentTimeMillis();
				//	versus.get(versus.indexOf(x));
					m = skipListNumber.searchNodeCounter(Math.round(Math.random()*1000000),false);
				//	time2 = System.currentTimeMillis();
					resRecherches += (m);
				}
			
				//suppression des nombres + temps de recherche
				for(int i=0;i<nboperation;i++){
					time1 = System.currentTimeMillis();
				//	versus.remove(x);
					skipListNumber.removeNode(Math.round(Math.random()*1000000));
					time2 = System.currentTimeMillis();
					resSuppression += (time2-time1);
				}
			
				 MoyresInsertions += resInsertions;
				 MoyresRecherches += resRecherches; 
				 MoyresSuppression += resSuppression;
			}
		
			System.out.println("Moyenne_Recherches: "+(float)MoyresRecherches/nboperation);
		/*	System.out.println("Moyenne_Suppressions: "+(float)MoyresSuppression/nbessais);
			System.out.println("Moyenne_Insertions: "+(float)MoyresInsertions/nbessais); */
		
		} 
		}catch  (InputMismatchException e) {
     			 System.out.println ("Mismatch exception:" + e);
			 System.exit(-1);
    		}
		
		
	}
}
