package fr.java.hosts.archs.x86.signals;
public class NoShutdownHookAllowed {
public static void main(String[] args) throws Exception {
  try {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        System.out.println("Shutdown hook going mad");
      }
    });
  } catch(IllegalArgumentException ex) {
    System.out.println("Caught " + ex);
  }
  Thread.sleep(10000);
}
}