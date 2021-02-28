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

## Step 3. Use it in your project
```kotlin
class MainActivity : AppCompatActivity() {

  private lateinit var openGraphManager: OpenGraphManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    openGraphManager = OpenGraphManager()
    openGraphManager.getMetaData("https://github.com/ReactiveX/RxAndroid")

    openGraphManager.mOnLinkPreview = object : OpenGraphManager.OnLinkPreview {
      @SuppressLint("SetTextI18n")
      override fun onSuccess(position: Int?, metaData: MetaData) {
        Log.d("Meta", "$metaData")
        findViewById<TextView>(R.id.meta).text =
          metaData.title + "\n" + metaData.description + "\n" + metaData.imageurl
      }

      override fun onError(position: Int?, throwable: Throwable) {
        Log.d("Meta", "$throwable")
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    openGraphManager.dispose()
  }
}
```

# Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Push your branch (git push origin my-new-feature)
5. Create a new Pull Request
