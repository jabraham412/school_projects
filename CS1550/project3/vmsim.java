/**
* @author John Abraham
* CS1550 - Operating Systems
* Project3: Virtual Memory Simulator
*
* command line:
* java vmsim –n <numframes> -a <opt|clock|nru|work> [-r <refresh>] [-t <tau>] <tracefile>
*
* enter one of the following options:
* java vmsim –n <numframes> -a <opt> <tracefile>
* java vmsim –n <numframes> -a <clock> <tracefile>
* java vmsim –n <numframes> -a <nru> -r <refresh> <tracefile>
* java vmsim –n <numframes> -a <work> -r <refresh> -t <tau> <tracefile>
*
* the numframes parameter is used to specify the numebr of page frames on the execution.
* for the numframes parameter, select: 8, 16, 32, 64, or 128.
*/

public class vmsim {

   public static void main(String[] args) {
      int nFrames = 0;
      String algorithm = "";
      int refresh = 0;
      int tau = 0;
      String traceFileName = "";
      Simulate sim = new Simulate();

      for (int i=0; i<args.length; ++i) {
         if (args[i].equals("-n")) {
            try {
               nFrames = Integer.parseInt(args[1]);
            } catch (Exception e) {
               printErrorMsg();
               System.out.println("\n*Please enter an integer for the numframes parameter.\n");
               System.exit(0);
            }
            if (nFrames!=8 && nFrames!=16 && nFrames!=32 && nFrames!=64 && nFrames!=128 ) {
               printErrorMsg();
               System.out.println("\n*Please enter 8, 16, 32, 64, or 128 for the numframe parameter.\n");
               System.exit(0);
            }
         } else if (args[i].equals("-a")) { algorithm = args[i+1]; }
         else if (args[i].equals("-r")) { refresh = Integer.parseInt(args[i+1]); }
         else if (args[i].equals("-t")) { tau = Integer.parseInt(args[i+1]); }
         else if (i == args.length-1) { traceFileName = args[i]; }
      }

      if (algorithm.equals("opt")) {
         sim.opt(nFrames, traceFileName);
      } else if (algorithm.equals("clock")) {
         sim.clock(nFrames, traceFileName);
      } else if (algorithm.equals("nru")) {
         sim.nru(nFrames, refresh, traceFileName);
      } else if (algorithm.equals("work")) {
         System.out.println("The working set algorithm is not currently supported.");
         //sim.work(nFrames, refresh, tau, traceFileName);
      } else {
         printErrorMsg();
         System.out.println("\n*Please enter opt|clock|nru|work for command-line parameter 3.\n");
         System.exit(0);
      }
   }

   public static void printErrorMsg() {
      System.out.println("Incorrect usage of the command-line parameters. Please enter one of the following:");
      System.out.println("java vmsim –n <numframes> -a <opt> <tracefile>");
      System.out.println("java vmsim –n <numframes> -a <clock> <tracefile>");
      System.out.println("java vmsim –n <numframes> -a <nru> -r <refresh> <tracefile>");
      System.out.println("java vmsim –n <numframes> -a <work> -r <refresh> -t <tau> <tracefile>");
   }
}