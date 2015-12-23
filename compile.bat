@echo off
javac -d "out/production/Engine" -cp "libs/lwjgl.jar;out/production/Engine/" src/info/markhillman/Models/*.java src/info/markhillman/Renderers/*.java src/info/markhillman/Loaders/*.java src/info/markhillman/Scene/*.java src/info/markhillman/Utils/*.java src/info/markhillman/*.java
pause