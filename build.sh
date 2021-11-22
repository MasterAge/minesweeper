#!/bin/sh

# Compile files
javac -d out/production/ src/*.java

# Create archive
jar --create --file minesweeper.jar -m src/META-INF/MANIFEST.MF

# Add files
pushd out/production; jar -f ../../minesweeper.jar -u *.class; popd
pushd src; jar -f ../minesweeper.jar -u Art; popd

echo "Build complete."
echo "Run with java -jar minesweeper.jar"
