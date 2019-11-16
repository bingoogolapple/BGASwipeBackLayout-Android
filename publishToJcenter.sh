#!/bin/bash +x

sed -i -e "s/\/\/ classpath 'com.novoda:bintray-release:0.8.0'/classpath 'com.novoda:bintray-release:0.8.0'/" build.gradle

./gradlew :library:clean :library:build :library:bintrayUpload -PpublishAar

sed -i -e "s/classpath 'com.novoda:bintray-release:0.8.0'/\/\/ classpath 'com.novoda:bintray-release:0.8.0'/" build.gradle