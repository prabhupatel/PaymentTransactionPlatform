How to run
-----------
1. clone project from git to host system
   git clone https://github.com/prabhupatel/PaymentTransactionPlatform.git
2. Go to project source directory
3. Open terminal or cmd prompt.
4. run command: chmod 777 .
5. execute below command to set the java home:
   export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home

6. Build the gradle project:
   ./gradlew build
7. run the below command to execute:
   java -jar build/libs/PaymentTransactionPlatform-1.0-SNAPSHOT.jar <AbsolutePath>/sample/data.txt

8. check result in log/console.
