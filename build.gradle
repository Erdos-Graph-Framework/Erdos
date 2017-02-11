apply plugin: 'java'
apply plugin: 'maven'

group 'com.github.erdos-graph-framework'
version '1.0-SNAPSHOT'

sourceCompatibility = 1.7
targetCompatibility = 1.8

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    testCompile "junit:junit:4.11"
}

task sourcesJar(type: Jar, dependsOn: classes) {
    classifier = 'sources'
    from sourceSets.main.allSource
}

artifacts {
    archives sourcesJar
}