package thread;

public class PrintAB {
	private final int max = 10;
	private int curr=1;
	private final Object object = new Object();
	public void printA(){
		synchronized(object){
			for(int i=0;i<max;){
				if(curr!=1){
					
					try {
						object.wait();
					} catch (InterruptedException e) {
						System.out.println("printA 中断了");
						e.printStackTrace();
					}
				}else{
					System.out.println("A"+"  curr="+curr +"  iA="+i);
					i++;
					curr++;
					object.notify();
				}
			}
		}
	}
	public void printB(){
		synchronized(object){
			for(int i=0;i<max;){
				if(curr!=2){
					
					try {
						object.wait();
					} catch (InterruptedException e) {
						System.out.println("printB 中断了");
						e.printStackTrace();
					}
				}else{
					System.out.println("B"+"  curr="+curr+"  iB="+i);
					i++;
					curr=1;
					object.notify();
				}
			}
		}
	}
}
