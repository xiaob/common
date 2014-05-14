package tmp.jdk.jdk7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

// http://www.oschina.net/question/54100_35439
public class PhaserTest {

	public static void main(String[] args) throws Exception {
		demo1();
		demo2();
		demo3();
	}

	public static void demo1() throws Exception {
		final Phaser phaser = new Phaser(3) {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.printf(
						"---> run here: onAdvance --> phase=%d, parties=%d%n",
						phase, registeredParties);
				return super.onAdvance(phase, registeredParties);
			}
		};
		new Thread() {
			@Override
			public void run() {
				System.out.println("---> run here: awaitAdvance 0 start");
				phaser.awaitAdvance(0);
				System.out.println("---> run here: awaitAdvance 0 end");
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				System.out.println("---> run here: awaitAdvance 1 start");
				phaser.awaitAdvance(1);
				System.out.println("---> run here: awaitAdvance 1 end");
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				System.out
						.println("---> run here: arriveAndAwaitAdvance start");
				phaser.arriveAndAwaitAdvance();
				System.out.println("---> run here: arriveAndAwaitAdvance end");
			}
		}.start();
		Thread.sleep(200);
		System.out.println("---> arriveAndDeregister 1");
		System.out.println("---> arriveAndDeregister 1 -> "
				+ phaser.arriveAndDeregister());
		Thread.sleep(200);
		System.out.println("---> arrive 1");
		System.out.println("---> arrive 1 -> " + phaser.arrive());
		Thread.sleep(200);
		System.out.println("---> register 1");
		phaser.register();
		Thread.sleep(200);
		System.out.println("---> arrive 2");
		System.out.println("---> arrive 2 -> " + phaser.arrive());
		Thread.sleep(200);
		System.out.println("---> arrive 3");
		System.out.println("---> arrive 3 -> " + phaser.arrive());
		Thread.sleep(200);
		System.out.println("---> arrive 4");
		System.out.println("---> arrive 4 -> " + phaser.arrive());
		Thread.sleep(200);
		System.out.println("---> arrive 5");
		System.out.println("---> arrive 5 -> " + phaser.arrive());
		Thread.sleep(200);
		System.out.println("---> arrive 6");
		System.out.println("---> arrive 6 -> " + phaser.arrive());
		Thread.sleep(200);
		System.out.println("---> arrive 7");
		System.out.println("---> arrive 7 -> " + phaser.arrive());
		Thread.sleep(200);
		System.out.println("---> arrive 8");
		System.out.println("---> arrive 8 -> " + phaser.arrive());
		Thread.sleep(200);
		System.out.println("---> arrive 9");
		System.out.println("---> arrive 9 -> " + phaser.arrive());
	}

	public static void demo2() {
		final List<Runnable> tasks = new ArrayList<Runnable>();
		for (int i = 0; i < 3; i++) {
			final int counter = i;
			tasks.add(new Runnable() {
				@Override
				public void run() {
					System.out.println("---> " + counter);
					try {
						Thread.sleep(200);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		final Phaser phaser = new Phaser(1); // "1" to register self
		// create and start threads
		for (final Runnable task : tasks) {
			phaser.register();
			new Thread() {
				@Override
				public void run() {
					phaser.arriveAndAwaitAdvance(); // await all creation
					task.run();
				}
			}.start();
		}
		phaser.arriveAndDeregister(); // allow threads to start and
		// deregister self
	}

	public static void demo3() {
		final List<Runnable> tasks = new ArrayList<Runnable>();
		for (int i = 0; i < 3; i++) {
			final int counter = i;
			tasks.add(new Runnable() {
				@Override
				public void run() {
					System.out.println("---> " + counter);
					try {
						Thread.sleep(200);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		final Phaser phaser = new Phaser() {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				return phase >= 5 || registeredParties == 0;
			}
		};
		phaser.register();
		for (final Runnable task : tasks) {
			phaser.register();
			new Thread() {
				@Override
				public void run() {
					do {
						task.run();
						phaser.arriveAndAwaitAdvance();
					} while (!phaser.isTerminated());
				}
			}.start();
		}
		phaser.arriveAndDeregister(); // deregister self, don't wait
	}

}
