# Erdos
a modular and modern Graph theoretic algorithms framework
### Dependencies
* none

## How to use

Option 1: Jitpack

Add Jitpack in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add to your dependencies:

```groovy
dependencies {
    compile 'com.github.HendrixString:Android-PdfMyXml:{Tag}' // the latest version is "v1.0.1"
}
```

Option 2: Simply fork or download the project, you can also download and create `.jar` file yourself, with

```bash
git clone https://github.com/Erdos-Graph-Framework/Erdos.git erdos
cd erdos
./gradlew build
ls -l build/libs
```

Option 3: grab the latest released jar

[https://github.com/Erdos-Graph-Framework/Erdos/releases](https://github.com/Erdos-Graph-Framework/Erdos/releases)

```bash

```

## Notable technical features
* compatible with `Java 7`
* compose your graph by features and engine. modular design.
* production proved code. Used in a commercial project.

## Supported graphs
* simple graph, directed and undirected
* multi edge graph, directed and undirected
* pseudo graph
* custom graph, configure by self-loops, multi-edges and a graph engine.

## builtin algorithms
Erdos is a framework to easily help you design graph algorithms
with the correct abstractions and utilities. The builtin algorithms are:
* search algorithms
    * [BFS](https://en.wikipedia.org/wiki/Breadth-first_search) - Breadth First Search
    * [DFS](https://en.wikipedia.org/wiki/Depth-first_search) - Depth First Search
* sorting
    * [Topological sorting](https://en.wikipedia.org/wiki/Topological_sorting)
* structure
    * [Strongly Connected Components](https://en.wikipedia.org/wiki/Strongly_connected_component)
* minimum spanning tree
    * [Prim's algorithm](https://en.wikipedia.org/wiki/Prim's_algorithm)
    * [Kruskal's algorithm](https://en.wikipedia.org/wiki/Kruskal's_algorithm)
* single source shortest path
    * [Bellman–Ford algorithm](https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm)
    * [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra's_algorithm)
    * Directed acyclic graph algorithm
* all pairs shortest path
    * [Floyd–Warshall algorithm](https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm)
    * [Johnson's algorithm](https://en.wikipedia.org/wiki/Johnson's_algorithm)
* minimum spanning tree
    * [Prim's algorithm](https://en.wikipedia.org/wiki/Prim's_algorithm)
    * [Kruskal's algorithm](hhttps://en.wikipedia.org/wiki/Kruskal's_algorithm)
* transformations
    * Square of a graph
    * transitive closure of a graph
    * transpose of a graph

## builtin graph engines
* **Adjacency** and **Incidence** list based graph engine <br/>designed for optimal complexity for algorithms that require more than a moderate edge queries.

### Instructions
#### 1. create XML layouts
First create XML layouts. give it dimensions in **pixels** (and for all it's sub views) and proportions according landscape or portrait according to ratio **1:1.41**.<br/><br/>
page1.xml
```java
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="2115px"
                android:layout_height="1500px"
                android:background="@color/white">
  <TextView android:id="@+id/tv_hello"
                android:textColor="@color/black"
                android:textSize="27px"
                android:textStyle="bold"
                android:padding="6px"/>

</RelativeLayout>
```

you can create as many as pages/templates as you need.

#### 2. Implement a View renderer
implement your View renderer by extending `AbstractViewRenderer` or by anonymously instantiating it and injecting the layout id. the initView(View view) will supply you an inflated View automatically. There are other options but I wont cover it now.
```java
AbstractViewRenderer page = new AbstractViewRenderer(context, R.layout.page1) {
    private String _text;

    public void setText(String text) {
        _text = text;
    }

    @Override
    protected void initView(View view) {
        TextView tv_hello = (TextView)view.findViewById(R.id.tv_hello);
         tv_hello.setText(_text);
    }
};

// you can reuse the bitmap if you want
page.setReuseBitmap(true);

```

#### 3. Build the PDF document
Use `PdfDocument` or `PdfDocument.Builder` to add pages and render and run it all at background with progress bar.
```java
PdfDocument doc            = new PdfDocument(ctx);

// add as many pages as you have
doc.addPage(page);

doc.setRenderWidth(2115);
doc.setRenderHeight(1500);
doc.setOrientation(PdfDocument.A4_MODE.LANDSCAPE);
doc.setProgressTitle(R.string.gen_please_wait);
doc.setProgressMessage(R.string.gen_pdf_file);
doc.setFileName("test");
doc.setInflateOnMainThread(false);
doc.setListener(new PdfDocument.Callback() {
    @Override
    public void onComplete(File file) {
        Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
    }

    @Override
    public void onError(Exception e) {
        Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
    }
});

doc.createPdf(ctx);

```

or use `PdfDocument.Builder`
```java
new PdfDocument.Builder(ctx).addPage(page).filename("test").orientation(PdfDocument.A4_MODE.LANDSCAPE)
                         .progressMessage(R.string.gen_pdf_file).progressTitle(R.string.gen_please_wait).renderWidth(2115).renderHeight(1500)
                         .listener(new PdfDocument.Callback() {
                             @Override
                             public void onComplete(File file) {
                                 Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
                             }

                             @Override
                             public void onError(Exception e) {
                                 Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
                             }
                         }).create().createPdf(this);
```

### Contributions
contributions are most welcomed, please consult [`CONTRIBUTING.MD`](CONTRIBUTING.MD)

### License
If you like it -> star or share it with others

```
Copyright (C) 2016 Tomer Shalev (https://github.com/HendrixString)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
```

### Contact Author
* [tomer.shalev@gmail.com](tomer.shalev@gmail.com)
* [Google+ TomershalevMan](https://plus.google.com/+TomershalevMan/about)
* [Facebook - HendrixString](https://www.facebook.com/HendrixString)