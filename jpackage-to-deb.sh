#!/bin/bash
set -e

APP=$1
VERSION=$2
ARCH=amd64

rm -rf build
./gradlew jpackageApp
JP_OUT=build/jpackage/$APP
ICON=icons/icon.png
PKG=${APP}_${VERSION}_${ARCH}

if [ -z "$APP" ] || [ -z "$VERSION" ]; then
  echo "Usage: ./jpackage-to-deb.sh <app-name> <version>"
  exit 1
fi

if [ ! -d "$JP_OUT" ]; then
  echo "❌ jpackage output not found: $JP_OUT"
  exit 1
fi

if [ ! -f "$ICON" ]; then
  echo "❌ icon.png not found in project root"
  exit 1
fi

echo "📁 Creating deb structure..."
rm -rf "$PKG"
mkdir -p "$PKG"/{DEBIAN,usr/bin,usr/lib/$APP}
mkdir -p "$PKG/usr/share/applications"
mkdir -p "$PKG/usr/share/icons/hicolor/256x256/apps"

echo "📝 Writing control file..."
cat > "$PKG/DEBIAN/control" <<EOF
Package: $APP
Version: $VERSION
Section: utils
Priority: optional
Architecture: $ARCH
Maintainer: Linux Java Repo
Description: $APP Java application packaged with jpackage
EOF

echo "🚀 Creating launcher..."
cat > "$PKG/usr/bin/$APP" <<EOF
#!/bin/bash
exec /usr/lib/$APP/bin/$APP "\$@"
EOF

chmod +x "$PKG/usr/bin/$APP"

echo "🖼 Installing icon..."
cp "$ICON" "$PKG/usr/share/icons/hicolor/256x256/apps/$APP.png"

echo "📄 Creating desktop entry..."
cat > "$PKG/usr/share/applications/$APP.desktop" <<EOF
[Desktop Entry]
Name=$APP
Exec=$APP
Icon=$APP
Type=Application
Categories=Utility;
EOF

echo "📦 Copying jpackage output..."
cp -r "$JP_OUT"/* "$PKG/usr/lib/$APP/"

echo "📦 Building deb package..."
dpkg-deb --build "$PKG"

echo "✅ Created ${PKG}.deb"

