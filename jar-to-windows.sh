#!/bin/bash
set -e

APP=$1
VERSION=$2
JAR=build/libs/$APP-$VERSION.jar

if [ -z "$APP" ] || [ -z "$VERSION" ]; then
  echo "Usage: ./jpackage-to-deb.sh <app-name> <version>"
  exit 1
fi

echo "📁 Creating lanuch4j xml..."
touch "launch4j.xml"
cat > "launch4j.xml" <<EOF
<launch4jConfig>
  <outfile>$APP.exe</outfile>
  <jar>$JAR</jar>
  <headerType>gui</headerType>
  <jre>
    <minVersion>17</minVersion>
  </jre>
</launch4jConfig>
EOF

echo "🚀 Creating launcher..."
bash launch4j/launch4j.sh launch4j.xml
echo " launch4j.xml"
echo "📦 Done!"

