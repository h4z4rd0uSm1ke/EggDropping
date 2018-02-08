package main;

/**
 * Distintas soluciones al problema del EggDropping
 * sin usar la fórmula convencional.
 * 
 * @author Miguel Igual
 *
 */
public class EggDropping {

	/**
	 * Piso desde el que se empiezan a romper los huevos.
	 */
	public static int criticalFloor=23;
	
	public static void main(String[] args) {
		//una prueba con todos los pisos
		for (int i=1; i<=100; i++) {
			criticalFloor=i;
			System.out.println("Drop100, tries: "+minEggDropper100());
		}
		for (int i=1; i<=100; i++) {
			criticalFloor=i;
			System.out.println("Drop2, tries: "+minEggDropper2());
		}
		
		//varias pruebas
		criticalFloor=121;
		System.out.println("DropX, tries: "+minEggDropperX(23, 321));
		criticalFloor=13;
		System.out.println("DropX, tries: "+minEggDropperX(4, 26));
		criticalFloor=45;
		System.out.println("DropX, tries: "+minEggDropperX(0, 3243));
		criticalFloor=34;
		System.out.println("DropX, tries: "+minEggDropperX(43, 0));
		criticalFloor=59;
		System.out.println("DropX, tries: "+minEggDropperX(2, 100));
	}
	
	//en 5 intentos máximo
	/**
	 * Función que devuelve el número mínimo de intentos 
	 * que se necesitan para decubrir el piso desde 
	 * el que se empiezan a romper los huevos. Utilizando
	 * hasta 100 huevos. Desde un edificio de 100 pisos.
	 * 
	 * @return número de minimo de intentos
	 */
	public static int minEggDropper100() {
		int minDrops100=0;
		int tries=0;
		int floors=100;
		
		tries=funcion100(floors);
		System.out.println("Intentos: "+tries);
		minDrops100=tries;
		
		return minDrops100;
		
	}
	
	//en 19 intentos máximo
	/**
	 * Función que devuelve el número mínimo de intentos 
	 * que se necesitan para decubrir el piso desde 
	 * el que se empiezan a romper los huevos. Utilizando
	 * sólo 2 huevos. Desde un edificio de 100 pisos.
	 * 
	 * @return número de intentos mínimo que se necesitan
	 */
	public static int minEggDropper2() {
		int minDrops2=0;
		int tries=0;
		int floors=100;
		
		tries=funcion2(floors);
		minDrops2=tries;
		
		return minDrops2;

	}
	
	/**
	 * Función que devuelve el número mínimo de intentos 
	 * que se necesitan para decubrir el piso desde 
	 * el que se empiezan a romper los huevos. 
	 * Utilizando hasta 'x' huevos, en un edificio de 'y' pisos.
	 * 
	 * @param eggs número de huevos a utilizar
	 * @param floors número de pisos del edificio
	 * 
	 * @return número de intentos mínimo que se necesitan 
	 *
	 */
	//en x intentos
	public static int minEggDropperX(int eggs, int floors) {
		int tries=0;
		int minDropsX=0;
		
	    //necesitamos un intento si hay un piso solamente
        if(floors==1) {
        	tries=1;
			System.out.println("Critical floor is: "+1);
        //si tenemos 0 pisos el numero de intentos sera 0
        }else if(floors==0) {
        	tries=0;
        	System.out.println("The building has no floors.");
        // si solo tenemos un huevo el numero de intentos habra de ser igual al de pisos
        }else if(eggs==1) {
     	   	tries=0;
        // si no tenemos huevos no podemos hacer nada
        }else if(eggs==0) {
     	   	tries=0;
     	   	System.out.println("There are no eggs to try with.");
        }
        //si el numero de huevos es igual a 2 usamos la funcion2
        else if(eggs==2) {
			tries=funcion2(floors);
		}
        //i el numero de huevos es mayor de 2 usamos la funcionX
		if(eggs>2){
			boolean terminate=false;
			//dividimos el edificio a la mitad
			//en caso de que el numero de pisos sea impar añadimos el resto
			int blockSize=floors/2+(floors%2);
			
			//probamos desde la mitad
			int currentTry=blockSize;
			
			while(!terminate) {
				//mientras tengamos mas de 2 huevos
				if(eggs > 2){
					if(criticalFloor>currentTry) {
						//no se rompe
						tries++;
						//mientras el bloque sea mayor de 3, lo dividimos
						if(blockSize>3) {
							//probamos desde la mitad del siguiente bloque
							//(en caso de que sea impar se añade el resto)
							blockSize=blockSize/2+(blockSize%2);
							currentTry+=blockSize;

						} 
						else {
							//recorremos el bloque
							for(int i=currentTry; i<currentTry+blockSize; i++) {
								if(criticalFloor>i) {
									//no se rompe, se sigue probando
								} else {
									//se rompe
									eggs--;
									
									System.out.println("Critical floor is: "+i);
									terminate=true;
									return tries;
								}
							}
							//Si recorremos todo el loop y no se rompe sera el ultimo intento en que se rompio
							System.out.println("CriticalFloor is "+(currentTry+blockSize));
							terminate=true;
						}

					}
					else {
						//se rompe
						eggs--;
						tries++;
						
						if(blockSize>3) {
							//probamos desde la mitad del bloque inferior
							//en caso de que sea impar se añade el resto
							blockSize=blockSize/2+(blockSize%2);
							currentTry-=blockSize;
						} else {
							for(int i=currentTry-blockSize; i<currentTry; i++) {
								if(criticalFloor>i) {
									//no se rompe
								} else {
									eggs--;
									//se rompe
									System.out.println("Critical floor is: "+i);
									terminate=true;
									return tries;
								}
							}
							//Si recorremos todo el loop y no se rompe sera el ultimo intento en que se rompio
							System.out.println("CriticalFloor is "+currentTry);
							terminate=true;
						}
					}
				}
				//cuando nos quedamos con 2 huevos cambiamos la estrategia
				if(criticalFloor>currentTry) {
					//no se rompe
					tries++;
					//probamos desde el siguiente bloque
					currentTry+=blockSize;
				}
				else {
					if(eggs>0) {
						eggs--;
						//se rompe
						tries++;
							
						//Probamos dentro del bloque anterior con el huevo que queda
						for(int i=(currentTry-blockSize)+1; i<currentTry; i++) {
							if(criticalFloor>i) {
								//no se rompe
								tries++;
							}
							else{
								//se rompe
								eggs--;
								tries++;
								System.out.println("CriticalFloor is "+i);
								terminate=true;
								return tries;
							}
						}
						//Si recorremos todo el loop y no se rompe sera el ultimo intento en que se rompio
						System.out.println("CriticalFloor is "+currentTry);
						terminate=true;
					}else {
						new Exception("No quedan huevos").printStackTrace();
					}
				}
				
				
			}
		}
		
		minDropsX=tries;
		
		return minDropsX;
		
	}
	
	
	/**
	 * Función que recorre una cantidad 'n' de pisos
	 * con 100 huevos. Y devuelve el número de
	 * intentos utilizados hasta descubrir el piso
	 * crítico.
	 * 
	 * @param floors número de pisos
	 * @return número de intentos utilizados
	 */
	public static int funcion100(int floors) {
		int tries=0;
		int eggs=100;
		boolean terminate=false;
		//dividimos el edificio a la mitad	
		int blockSize=floors/2;
		//empezamos probando desde la mitad
		int currentTry=blockSize;
		
		while(!terminate) {
			//si el piso critico es mayor
			if(criticalFloor>currentTry) {
				//no se rompe
				tries++;
				
				//mientras el bloque sea mayor de 3, lo dividimos
				if(blockSize>3) {
					//probamos desde la mitad del siguiente bloque
					//(en caso de que sea impar se añade el resto)
					blockSize=blockSize/2+(blockSize%2);
					currentTry+=blockSize;

				} 
				else {
					for(int i=currentTry; i<currentTry+blockSize; i++) {
						if(criticalFloor>i) {
							//no se rompe
							//se sigue probando
						} else {
							eggs--;
							//se rompe
							System.out.println("Critical floor is: "+i);
							terminate=true;
							return tries;
						}
					}
					//Si recorremos todo el loop y no se rompe sera el ultimo intento en que se rompio
					System.out.println("criticalFloor is "+(currentTry+blockSize));
					terminate=true;
				}

			}
			else {
				//se rompe
				eggs--;
				tries++;
				
				if(blockSize>3) {
					//probamos desde la mitad del bloque inferior
					//(en caso de que sea impar se añade el resto)
					blockSize=blockSize/2+(blockSize%2);
					currentTry-=blockSize;
				} else {
					for(int i=currentTry-blockSize; i<currentTry; i++) {
						if(criticalFloor>i) {
							//no se rompe
						} else {
							eggs--;
							//se rompe
							System.out.println("Critical floor is: "+i);
							terminate=true;
							return tries;
						}
					}
					//Si recorremos todo el loop y no se rompe sera el ultimo intento en que se rompio
					System.out.println("criticalFloor is "+currentTry);
					terminate=true;
				}
				
			}
		}
		System.out.println("broken eggs: "+(100-eggs));
		return tries;
			
	}
	
	/**
	 * Función que recorre una cantidad 'n' de pisos
	 * con 2 huevos. Y devuelve el número de
	 * intentos utilizados hasta descubrir el piso
	 * crítico.
	 * 
	 * @param floors número de pisos
	 * @return número de intentos utilizados
	 */
	public static int funcion2(int floors) {
		int tries=0;
		boolean terminate=false;
		int eggs=2;
		//dividimos el edificio en bloques de 10
		int blockSize=floors/10;
		int currentTry=blockSize;
		
		while(!terminate) {
			if(criticalFloor>currentTry) {
				//no se rompe
				tries++;
				//probamos desde el siguiente bloque
				currentTry+=blockSize;
			}
			else {
				if(eggs>0) {
					eggs--;
					//se rompe
					tries++;
						
					//Probamos dentro del bloque anterior con el huevo que queda
					for(int i=(currentTry-blockSize)+1; i<currentTry; i++) {
						if(criticalFloor>i) {
							//no se rompe
							tries++;
						}
						else{
							//se rompe
							eggs--;
							tries++;
							System.out.println("criticalFloor is "+i);
							terminate=true;
							return tries;
						}
					}
					//Si recorremos todo el loop y no se rompe sera el ultimo intento en que se rompio
					System.out.println("criticalFloor is "+currentTry);
					terminate=true;
				}else {
					new Exception("No quedan huevos");
				}
			}
		}
		return tries;
				
	}

}
