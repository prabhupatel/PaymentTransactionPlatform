How to run
-----------

1. Go to project source directory 
2. Open terminal or cmd prompt.
3. execute below command to set the java home
   export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home

4. Build the gradle project
   ./gradlew build
5. run the below command to execute
   java -jar build/libs/PaymentTransactionPlatform-1.0-SNAPSHOT.jar <AbsolutePath>/sample/data.txt

6. check result in log/console.
