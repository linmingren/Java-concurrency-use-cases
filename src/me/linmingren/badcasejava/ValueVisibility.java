package me.linmingren.badcasejava;


/**
 * ������Ӹ�һ���ͬ����̫һ������Ϊ��������еĶ��߳����ⶼ��ĳһʱ�̵�״̬û��ͬ������ġ�
 * �����������״̬��Զ������ͬ����
 * 
 * ���������
 * 1.��stop����volatile
 * 2. �� stop ����ΪAtomicBoolean
 * 
 * @author linmingren
 *
 */
public class ValueVisibility {
	static boolean stop = false;
	
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stop) {
				}
			}
			
		});
        thread.start();

        //��������ߺ���Ҫ����Ȼstop�ĵ�ֵ�ᱻ������
        //�ο�<Java �������ʵ�������İ��34ҳ�����ӣ��Ǹ��������ҵĻ������ܲ���������Ľ��,������Ϊû����仰
        Thread.sleep(1000);
        stop = true;
        System.out.println("���̵߳�stop�Ѿ���true");
	}

}
