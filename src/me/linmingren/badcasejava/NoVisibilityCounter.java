package me.linmingren.badcasejava;

import java.util.ArrayList;
import java.util.List;

/**
 * ��������������߳���ͬʱ����һ����������ÿ���̵߳���INC_COUNT�Ρ�
 * ������������ֵ��INC_COUNT * �߳��������ǽ�����������ܴ�
 * 
 * �����������߳�1�Ķ�counter���޸��߳�2���ἰʱ��������ġ�
 * �����߳�1�Ѿ���counter�ӵ�100�ˣ���û��д�����ڴ�
 * ��ô�߳�2������ֵ����0�����߳�2Ҳ��counter�ӵ�100����ʱ�����̶߳���ֵд�����ڴ�
 * ������Ǵ�Ҽ���200�Σ��������߳̿�����ֵȷ��100.
 * 
 * 
 * 
 * �������
 * 1. ��incCounter����ͬ��
 * 2. ��counter���AtomicInteger, ע���volatile������Ҫ������ֵû��
 * 
 * 
 * @author linmingren
 *
 */
public class NoVisibilityCounter {

	private static final int INC_COUNT = 10000;

	static int counter = 0;
	
	public static void incCounter() {
		++ counter;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("��ʼ����������");
		
		List<Thread> threadList = new ArrayList<Thread>();
		int threadNum = 2;
		for (int i =0 ;i < threadNum; ++i) {
			 new Thread(new IncreaseCounterTask()).start();
		}
		
        for (Thread t : threadList) {
        	t.join();
        }
		

		int actualCounter = counter;
		int expectedCount = INC_COUNT * threadNum;
		if (actualCounter != expectedCount) {
			System.err.printf(
					"��������! ʵ��ֵ %s, ����ֵ: %s.",
					actualCounter, expectedCount);
		} else {
			System.out.println("������ȷ!");
		}
	}

	private static class IncreaseCounterTask implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < INC_COUNT; ++i) {
				incCounter();
			}
		}
	}
}
