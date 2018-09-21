package thread;

public class Print {
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
					object.notifyAll();
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
					curr++;
					object.notifyAll();
				}
			}
		}
	}
	public void printC(){
		synchronized(object){
			for(int i=0;i<max;){
				if(curr!=3){
					
					try {
						object.wait();
					} catch (InterruptedException e) {
						System.out.println("printC 中断了");
						e.printStackTrace();
					}
				}else{
					System.out.println("C"+"  curr="+curr+"  iC="+i);
					i++;
					curr=1;
					object.notifyAll();
				}
			}
		}
	}
	
}
