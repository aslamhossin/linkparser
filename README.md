# LinkParser
LinkParser is a small class used to represent the Open Graph protocol

# How to
To get a Git project into your build:

## Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
		
		maven { url 'https://jitpack.io' }
		maven { url "https://oss.jfrog.org/libs-snapshot" }
		}
	}
## Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.mdaslamHossin:linkparser:1'
	}
