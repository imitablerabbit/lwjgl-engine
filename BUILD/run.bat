@echo off
cd src
java -Djava.library.path=../libs/ -cp ".;../libs/lwjgl.jar" com.markhillman.Main