echo off

javac -d . *.java

jar cfv CCApp.jar .


rem keytool -genkey -alias <alias> -keystore <filename> -keypass <keypass> -dname <dname> -storepass <storepass> -validity <days>

keytool -genkey -alias myAlias -keystore myCert -keypass myKeyPass -dname "CN=CCApp" -storepass myStorePass -validity 1825


rem jarsigner -keystore <keystore_file> -keypass <keypass> -storepass <storepass> <jar_file> <alias>

jarsigner -keystore myCert -keypass myKeyPass -storepass myStorePass CCApp.jar myAlias

jarsigner -verify CCApp.jar

pause